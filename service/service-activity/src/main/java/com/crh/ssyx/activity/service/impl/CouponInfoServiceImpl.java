package com.crh.ssyx.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crh.ssyx.activity.mapper.CouponInfoMapper;
import com.crh.ssyx.activity.mapper.CouponRangeMapper;
import com.crh.ssyx.activity.service.CouponInfoService;
import com.crh.ssyx.client.product.ProductFeignClient;
import com.crh.ssyx.enums.CouponRangeType;
import com.crh.ssyx.model.activity.CouponInfo;
import com.crh.ssyx.model.activity.CouponRange;
import com.crh.ssyx.model.product.Category;
import com.crh.ssyx.model.product.SkuInfo;
import com.crh.ssyx.vo.activity.CouponRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author crh
 * @since 2023-11-27
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {

    @Autowired
    private CouponRangeMapper couponRangeMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public IPage<CouponInfo> selectCouponInfo(Long page, Long limit) {
        Page<CouponInfo> pageParam = new Page<>(page, limit);
        IPage<CouponInfo> couponInfoPage = baseMapper.selectPage(pageParam, null);
        List<CouponInfo> couponInfoList = couponInfoPage.getRecords();
        for (CouponInfo item : couponInfoList) {
            item.setCouponTypeString(item.getCouponType().getComment());
            CouponRangeType rangeType = item.getRangeType();
            if (!StringUtils.isEmpty(rangeType)) {
                item.setRangeTypeString(rangeType.getComment());
            }
        }
        return couponInfoPage;
    }

    //根据id查询优惠券
    @Override
    public CouponInfo getCouponInfo(Long id) {
        CouponInfo couponInfo = baseMapper.selectById(id);
        couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
        if (couponInfo.getRangeType() != null) {
            couponInfo.setRangeTypeString(couponInfo.getRangeType().getComment());
        }
        return couponInfo;
    }

    //根据优惠券id查询规则信息
    @Override
    public Map<String, Object> findCouponRuleList(Long id) {
        //第一步 根据优惠券id查询优惠券基本信息 coupon_info
        CouponInfo couponInfo = baseMapper.selectById(id);
        //第二步 根据优惠券id查询优惠范围coupon_range range_id
        LambdaQueryWrapper<CouponRange> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CouponRange::getCouponId, id);
        List<CouponRange> couponRangeList = couponRangeMapper.selectList(wrapper);
        //couponRangeList获取所有range_id
        List<Long> rangeIdList = couponRangeList.stream().map(CouponRange::getRangeId).collect(Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        //判断不同类型
        if (!CollectionUtils.isEmpty(rangeIdList)) {
            if (couponInfo.getRangeType() == CouponRangeType.SKU) {
                //如果 分类类型为SKU range_id 就是 skuId
                //远程调用根据多个skuId值获取对应sku信息
                List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(rangeIdList);
                result.put("skuInfoList", skuInfoList);
            } else if (couponInfo.getRangeType() == CouponRangeType.CATEGORY) {
                //如果 分类类型为CATEGORY range_id 就是 分类Id
                //远程调用根据多个分类Id值获取对应分类信息
                List<Category> categoryList = productFeignClient.findCategoryList(rangeIdList);
                result.put("categoryList", categoryList);
            }
        }
        return result;
    }

    //添加优惠券规则数据
    @Override
    public void saveCouponRule(CouponRuleVo couponRuleVo) {
        //根据优惠券id删除规则数据
        LambdaQueryWrapper<CouponRange> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CouponRange::getCouponId, couponRuleVo.getCouponId());
        couponRangeMapper.delete(wrapper);

        //更新优惠券基本信息
        CouponInfo couponInfo = baseMapper.selectById(couponRuleVo.getCouponId());
        couponInfo.setRangeType(couponRuleVo.getRangeType());
        couponInfo.setConditionAmount(couponRuleVo.getConditionAmount());
        couponInfo.setAmount(couponRuleVo.getAmount());
        couponInfo.setRangeDesc(couponRuleVo.getRangeDesc());
        baseMapper.updateById(couponInfo);

        //添加新的优惠券规则数据
        List<CouponRange> couponRangeList = couponRuleVo.getCouponRangeList();
        for (CouponRange couponRange : couponRangeList) {
            //设置优惠券id
            couponRange.setCouponId(couponRuleVo.getCouponId());
            couponRangeMapper.insert(couponRange);
        }
    }
}
