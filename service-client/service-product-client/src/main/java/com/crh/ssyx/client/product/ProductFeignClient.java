package com.crh.ssyx.client.product;

import com.crh.ssyx.model.product.Category;
import com.crh.ssyx.model.product.SkuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "service-product")
public interface ProductFeignClient {

    @GetMapping("/api/product/inner/getCategory/{categoryId}")
    Category getCategory(@PathVariable("categoryId") Long categoryId);

    @GetMapping("/api/product/inner/getSkuInfo/{skuId}")
    SkuInfo getSkuInfo(@PathVariable("skuId") Long skuId);

    //根据skuId列表得到sku列表
    @PostMapping(value = "/api/product/inner/findSkuInfoList")
    List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdLists);

    //根据分类Id获取分类信息列表
    @PostMapping(value = "/api/product/inner/findCategoryList")
    List<Category> findCategoryList(@RequestBody List<Long> categoryList);

    //根据关键字查询匹配sku信息
    @GetMapping(value = "/api/product/inner/findSkuInfoByKeyword/{keyword}")
    List<SkuInfo> findSkuInfoByKeyword(@PathVariable("keyword") String keyword);

    //获取分类信息
    @GetMapping("/api/product/inner/findAllCategoryList")
    List<Category> findAllCategoryList();

    //获取新人专享
    @GetMapping("/api/product/inner/findNewPersonSkuInfoList")
    List<SkuInfo> findNewPersonSkuInfoList();


}
