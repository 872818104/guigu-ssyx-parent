package com.crh.ssyx.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crh.ssyx.activity.mapper.ActivityInfoMapper;
import com.crh.ssyx.activity.mapper.ActivityRuleMapper;
import com.crh.ssyx.activity.mapper.ActivitySkuMapper;
import com.crh.ssyx.activity.service.ActivityInfoService;
import com.crh.ssyx.client.product.ProductFeignClient;
import com.crh.ssyx.model.activity.ActivityInfo;
import com.crh.ssyx.model.activity.ActivityRule;
import com.crh.ssyx.model.activity.ActivitySku;
import com.crh.ssyx.model.product.SkuInfo;
import com.crh.ssyx.vo.activity.ActivityRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author crh
 * @since 2023-11-27
 */
@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo> implements ActivityInfoService {

    @Autowired
    private ActivityRuleMapper activityRuleMapper;

    @Autowired
    private ActivitySkuMapper activitySkuMapper;

    @Autowired
    private ProductFeignClient productFeignClient;


    //营销活动列表接口
    @Override
    public IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam) {
        IPage<ActivityInfo> activityInfoPage = baseMapper.selectPage(pageParam, null);
        //分页查询对象里面获取列表数据
        List<ActivityInfo> activityInfoList = activityInfoPage.getRecords();
        activityInfoList.forEach(item -> {
            item.setActivityTypeString(item.getActivityType().getComment());
        });
        return activityInfoPage;
    }


    //根据活动id获取活动规则数据
    @Override
    public Map<String, Object> findActivityRuleList(Long id) {
        //1.根据活动id,查询活动规则表activity_rule
        Map<String, Object> result = new HashMap<>();
        LambdaQueryWrapper<ActivityRule> activityRule = new LambdaQueryWrapper<>();
        activityRule.eq(ActivityRule::getActivityId, id);
        List<ActivityRule> activityRuleList = activityRuleMapper.selectList(activityRule);
        result.put("activityRuleList", activityRuleList);

        //2.根据活动id,查询实用规则商品skuId列表activity_sku
        LambdaQueryWrapper<ActivitySku> activitySku = new LambdaQueryWrapper<>();
        activitySku.eq(ActivitySku::getActivityId, id);
        List<ActivitySku> activitySkuList = activitySkuMapper.selectList(activitySku);
        //获取所有skuId
        List<Long> skuIdList = activitySkuList.stream().map(ActivitySku::getSkuId).collect(Collectors.toList());
        //2.1通过远程调用得到service-product模块接口，根据 skuId列表 得到商品信息
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(skuIdList);
        result.put("skuInfoList", skuInfoList);
        return result;
    }


    //新增活动规则
    @Override
    public void saveActivityRule(ActivityRuleVo activityRuleVo) {
        //第一步 根据活动id删除之前的规则数据
        //1.ActivityRule数据删除
        Long activityId = activityRuleVo.getActivityId();
        LambdaQueryWrapper<ActivityRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityRule::getActivityId, activityId);
        activityRuleMapper.delete(wrapper);

        //2.ActivitySku数据删除
        LambdaQueryWrapper<ActivitySku> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivitySku::getActivityId, activityId);
        activitySkuMapper.delete(queryWrapper);

        //第二步 获取规则列表数据
        List<ActivityRule> activityRuleList = activityRuleVo.getActivityRuleList();
        ActivityInfo activityInfo = baseMapper.selectById(activityId);
        for (ActivityRule activityRule : activityRuleList) {
            activityRule.setActivityId(activityId);//活动id
            activityRule.setActivityType(activityInfo.getActivityType());//活动类别
            activityRuleMapper.insert(activityRule);
        }

        //第三步 获取规则范围数据
        List<ActivitySku> activitySkuList = activityRuleVo.getActivitySkuList();
        for (ActivitySku activitySku : activitySkuList) {
            activitySku.setActivityId(activityId);
            activitySkuMapper.insert(activitySku);
        }

    }

    //3.根据关键字查询匹配sku信息
    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        //第一步 根据关键字查询sku匹配列表功能
        //(1)service-product块创建接口 据关键字查询sku匹配内容列表
        //(2)service-activity远程调用得到sku内容列表
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoByKeyword(keyword);
        //判断：如果没有搜索到，返回空值
        if (skuInfoList.size() == 0) {
            return skuInfoList;
        }
        //获取skuInfoList中所有商品id（skuId）
        List<Long> skuIdList = skuInfoList.stream().map(SkuInfo::getId).collect(Collectors.toList());
        //第二步 判断商品是否已经参与活动
        //如果商品在活动时间内，排除
        ///1.查询两张表判断activity_info activity_sku,编写sql语句
        List<Long> existskuIdList = baseMapper.selectSkuIdListExist(skuIdList);

        ///2.判断逻辑：排除已经参加活动的商品
        List<SkuInfo> findSkuList = new ArrayList<>();
        //遍历全部sku列表
        for (SkuInfo skuInfo : skuInfoList) {
            if (!existskuIdList.contains(skuInfo.getId())) {
                findSkuList.add(skuInfo);
            }
        }
        return findSkuList;
    }
}
