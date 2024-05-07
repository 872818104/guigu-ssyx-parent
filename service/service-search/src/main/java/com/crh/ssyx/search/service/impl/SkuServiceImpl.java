package com.crh.ssyx.search.service.impl;

import com.crh.ssyx.client.product.ProductFeignClient;
import com.crh.ssyx.enums.SkuType;
import com.crh.ssyx.model.product.Category;
import com.crh.ssyx.model.product.SkuInfo;
import com.crh.ssyx.model.search.SkuEs;
import com.crh.ssyx.search.repository.SkuRepository;
import com.crh.ssyx.search.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @program: guigu-ssyx-parent
 * @description:
 * @author: caoruhao
 * @create: 2023-11-21 10:16
 **/
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private ProductFeignClient productFeignClient;


    //上架
    @Override
    public void upperSku(Long skuId) {
        //通过远程调用，根据skuId获取商品信息
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        //获取分类信息
        Category category = productFeignClient.getCategory(skuInfo.getCategoryId());
        SkuEs skuEs = new SkuEs();
        //封装分类
        if (category != null) {
            skuEs.setCategoryId(category.getId());
            skuEs.setCategoryName(category.getName());
        }
        //封装sku
        skuEs.setId(skuInfo.getId());
        skuEs.setKeyword(skuInfo.getSkuName() + "," + skuEs.getCategoryName());
        skuEs.setWareId(skuInfo.getWareId());
        skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
        skuEs.setImgUrl(skuInfo.getImgUrl());
        skuEs.setTitle(skuInfo.getSkuName());
        if (Objects.equals(skuInfo.getSkuType(), SkuType.COMMON.getCode())) {
            skuEs.setSkuType(0);
            skuEs.setPrice(skuInfo.getPrice().doubleValue());
            skuEs.setStock(skuInfo.getStock());
            skuEs.setSale(skuInfo.getSale());
            skuEs.setPerLimit(skuInfo.getPerLimit());
        } else {
            //TODO 待完善-秒杀商品
        }
        SkuEs save = skuRepository.save(skuEs);
    }

    //下架
    @Override
    public void lowerSku(Long skuId) {
        skuRepository.deleteById(skuId);
    }

    @Override
    public List<SkuEs> findHotSkuList() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<SkuEs> pageModel = skuRepository.findByOrderByHotScoreDesc(pageable);
        return pageModel.getContent();
    }

}
