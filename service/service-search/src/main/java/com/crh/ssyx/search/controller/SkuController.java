package com.crh.ssyx.search.controller;

import com.crh.ssyx.common.result.Result;
import com.crh.ssyx.search.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: guigu-ssyx-parent
 * @description: 商品上下架
 * @author: caoruhao
 * @create: 2023-11-21 09:51
 **/

@RestController
@RequestMapping("api/search/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    //商品上架
    @GetMapping("inner/upperSku/{skuId}")
    public Result upperSku(@PathVariable Long skuId) {
        skuService.upperSku(skuId);
        return Result.ok(null);
    }

    //商品下架
    @GetMapping("inner/lowerSku/{skuId}")
    public Result lowerSku(@PathVariable Long skuId) {
        skuService.lowerSku(skuId);
        return Result.ok(null);
    }

}
