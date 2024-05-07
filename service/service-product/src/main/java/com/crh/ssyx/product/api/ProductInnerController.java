package com.crh.ssyx.product.api;

import com.crh.ssyx.model.product.Category;
import com.crh.ssyx.model.product.SkuInfo;
import com.crh.ssyx.product.service.CategoryService;
import com.crh.ssyx.product.service.SkuInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: guigu-ssyx-parent
 * @description: 远程调用方法, 同步到ES中
 * @author: caoruhao
 * @create: 2023-11-21 18:46
 **/
@RestController
@RequestMapping("api/product")
public class ProductInnerController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuInfoService skuInfoService;

    //根据分类Id获取分类信息
    @GetMapping("inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable Long categoryId) {
        return categoryService.getById(categoryId);
    }

    //根据skuId获取sku信息
    @GetMapping("inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable("skuId") Long skuId) {
        return skuInfoService.getById(skuId);
    }

    //根据skuId列表得到sku信息列表
    @PostMapping(value = "inner/findSkuInfoList")
    public List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdLists) {
        return skuInfoService.findSkuInfoList(skuIdLists);
    }

    //根据分类Id获取分类信息列表
    @PostMapping(value = "inner/findCategoryList")
    public List<Category> findCategoryList(@RequestBody List<Long> categoryList) {
        return categoryService.listByIds(categoryList);
    }


    //根据关键字查询匹配sku信息
    @RequestMapping(value = "inner/findSkuInfoByKeyword/{keyword}", method = RequestMethod.GET)
    public List<SkuInfo> findSkuInfoByKeyword(@PathVariable("keyword") String keyword) {
        return skuInfoService.findSkuInfoByKeyword(keyword);
    }

    @ApiOperation(value = "获取所有分类信息")
    @GetMapping("inner/findAllCategoryList")
    public List<Category> findAllCategoryList() {
        return categoryService.findAllList();
    }

    @ApiOperation(value = "获取新人专享")
    @GetMapping("inner/findNewPersonSkuInfoList")
    public List<SkuInfo> findNewPersonSkuInfoList() {
        return skuInfoService.findNewPersonSkuInfoList();
    }
}
