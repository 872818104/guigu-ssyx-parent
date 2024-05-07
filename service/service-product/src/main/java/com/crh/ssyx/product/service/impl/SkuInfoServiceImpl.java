package com.crh.ssyx.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crh.ssyx.model.product.SkuAttrValue;
import com.crh.ssyx.model.product.SkuImage;
import com.crh.ssyx.model.product.SkuInfo;
import com.crh.ssyx.model.product.SkuPoster;
import com.crh.ssyx.mq.constant.MqConst;
import com.crh.ssyx.mq.service.RabbitService;
import com.crh.ssyx.product.mapper.SkuInfoMapper;
import com.crh.ssyx.product.service.SkuAttrValueService;
import com.crh.ssyx.product.service.SkuImageService;
import com.crh.ssyx.product.service.SkuInfoService;
import com.crh.ssyx.product.service.SkuPosterService;
import com.crh.ssyx.vo.product.SkuInfoQueryVo;
import com.crh.ssyx.vo.product.SkuInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    @Autowired
    private SkuImageService skuImageService;

    @Autowired
    private SkuAttrValueService skuAttrValueService;

    @Autowired
    private SkuPosterService skuPosterService;

    @Autowired
    private RabbitService rabbitService;


    @Override
    public IPage<SkuInfo> selectPage(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo) {
        //获取条件值
        String keyword = skuInfoQueryVo.getKeyword();
        String skuType = skuInfoQueryVo.getSkuType();
        Long categoryId = skuInfoQueryVo.getCategoryId();

        //封装条件
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like(SkuInfo::getSkuName, keyword);
        }
        if (!StringUtils.isEmpty(skuType)) {
            wrapper.eq(SkuInfo::getSkuType, skuType);
        }
        if (!StringUtils.isEmpty(categoryId)) {
            wrapper.eq(SkuInfo::getCategoryId, categoryId);
        }
        return baseMapper.selectPage(pageParam, wrapper);
    }

    /**
     * 添加sku商品
     *
     * @param skuInfoVo
     */
    @Override
    public void saveSkuInfoVo(SkuInfoVo skuInfoVo) {
        //1.添加sku基本信息
        SkuInfo skuInfo = new SkuInfo();
        ////SkuInfoVo 复制到 SkuInfo
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.insert(skuInfo);

        //保存海报
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            int sort = 1;
            //便利,像每个海报对象添加商品id
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuInfo.getId());
                sort++;
            }
            skuPosterService.saveBatch(skuPosterList);
        }

        //保存图片
        List<SkuImage> skuImageList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImageList)) {
            int sort = 1;
            for (SkuImage skuImage : skuImageList) {
                skuImage.setSkuId(skuInfo.getId());
                skuImage.setSort(sort);
                sort++;
            }
            skuImageService.saveBatch(skuImageList);
        }

        //保存sku平台属性
        List<SkuAttrValue> skuAttrValues = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValues)) {
            int sort = 1;
            for (SkuAttrValue skuAttrValue : skuAttrValues) {
                skuAttrValue.setSkuId(skuInfo.getId());
                skuAttrValue.setSort(sort);
                sort++;
            }
            skuAttrValueService.saveBatch(skuAttrValues);
        }


    }

    //获取sku
    @Override
    public SkuInfo getSkuInfo(Long id) {
        SkuInfoVo skuInfoVo = new SkuInfoVo();
        SkuInfo skuInfo = baseMapper.selectById(id);

        //图片
        List<SkuImage> skuImageList = skuImageService.getSkuImageById(id);
        //海报
        List<SkuPoster> skuPosterList = skuPosterService.getSkuPosterById(id);
        //属性
        List<SkuAttrValue> skuAttrValues = skuAttrValueService.getSkuAttrValuesById(id);

        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        skuInfoVo.setSkuImagesList(skuImageList);
        skuInfoVo.setSkuPosterList(skuPosterList);
        skuInfoVo.setSkuAttrValueList(skuAttrValues);
        return skuInfo;
    }

    //商品SKU修改功能
    @Override
    public void updateBySkuInfo(SkuInfoVo skuInfoVo) {
        //修改基本信息
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        Long skuId = skuInfoVo.getId();
        baseMapper.updateById(skuInfoVo);
        //删除sku海报
        LambdaQueryWrapper<SkuPoster> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuPoster::getSkuId, skuId);
        skuPosterService.remove(wrapper);
        //保存sku海报
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuId);
            }
            skuPosterService.saveBatch(skuPosterList);
        }

        //删除sku图片
        LambdaQueryWrapper<SkuImage> imageWrapper = new LambdaQueryWrapper<>();
        imageWrapper.eq(SkuImage::getSkuId, skuId);
        skuImageService.remove(imageWrapper);
        //保存sku图片
        List<SkuImage> skuImageList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImageList)) {
            int sort = 1;
            for (SkuImage skuImage : skuImageList) {
                skuImage.setSkuId(skuId);
                skuImage.setSort(sort);
                sort++;
            }
            skuImageService.saveBatch(skuImageList);
        }


        //删除sku平台属性
        LambdaQueryWrapper<SkuAttrValue> valueWrapper = new LambdaQueryWrapper<>();
        valueWrapper.eq(SkuAttrValue::getSkuId, skuId);
        skuAttrValueService.remove(valueWrapper);

        //保存sku平台属性
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            int sort = 1;
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuId);
                skuAttrValue.setSort(sort);
                sort++;
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }

    }

    //商品上下架
    @Override
    public void publish(Long skuId, Integer status) {
        if (status == 1) {
            //上架
            SkuInfo skuInfoUp = new SkuInfo();
            skuInfoUp.setId(skuId);
            skuInfoUp.setPublishStatus(1);
            baseMapper.updateById(skuInfoUp);
            //整合mq把数据同步到es中
            rabbitService.sendMessage(MqConst.EXCHANGE_GOODS_DIRECT, MqConst.ROUTING_GOODS_UPPER, skuId);
        } else {
            //下架
            SkuInfo skuInfoUp = new SkuInfo();
            skuInfoUp.setId(skuId);
            skuInfoUp.setPublishStatus(0);
            baseMapper.updateById(skuInfoUp);
            //整合mq把数据同步到es中
            rabbitService.sendMessage(MqConst.EXCHANGE_GOODS_DIRECT, MqConst.ROUTING_GOODS_LOWER, skuId);
        }

    }


    //新人专享
    @Override
    public void isNewPerson(Long skuId, Integer status) {
        SkuInfo skuInfoNew = new SkuInfo();
        skuInfoNew.setId(skuId);
        skuInfoNew.setIsNewPerson(status);
        baseMapper.updateById(skuInfoNew);
    }

    //商品审核
    @Override
    public void check(Long skuId, Integer status) {
        // 更改发布状态
        SkuInfo skuInfoUp = new SkuInfo();
        skuInfoUp.setId(skuId);
        skuInfoUp.setCheckStatus(status);
        baseMapper.updateById(skuInfoUp);
    }

    //根据skuId列表得到sku列表
    @Override
    public List<SkuInfo> findSkuInfoList(List<Long> skuIdLists) {
        return baseMapper.selectBatchIds(skuIdLists);
    }

    //根据关键字查询匹配sku信息
    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(SkuInfo::getSkuName, keyword);
        return baseMapper.selectList(wrapper);
    }

    //获取新人专享商品
    @Override
    public List<SkuInfo> findNewPersonSkuInfoList() {
        //条件1：is_new_person = 1
        //条件2：publish_status = 0
        //条件3：显示其中3个

        //调用分页方法，显示其中三个
        Page<SkuInfo> infoPage = new Page<>(1, 3);
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuInfo::getIsNewPerson, 1);
        wrapper.eq(SkuInfo::getPublishStatus, 0);
        wrapper.orderByDesc(SkuInfo::getStock); //库存排序
        //调用方法查询
        IPage<SkuInfo> skuInfoPage = baseMapper.selectPage(infoPage, wrapper);
        return skuInfoPage.getRecords();
    }

}
