package com.crh.ssyx.product.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crh.ssyx.common.result.Result;
import com.crh.ssyx.model.product.SkuInfo;
import com.crh.ssyx.product.service.SkuInfoService;
import com.crh.ssyx.vo.product.SkuInfoQueryVo;
import com.crh.ssyx.vo.product.SkuInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(value = "SkuInfo管理", tags = "商品Sku管理")
@RestController
@RequestMapping(value = "/admin/product/skuInfo")
public class SkuInfoController {

    @Autowired
    private SkuInfoService skuInfoService;

    @ApiOperation(value = "获取sku分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "skuInfoQueryVo", value = "查询对象")
                        SkuInfoQueryVo skuInfoQueryVo) {
        Page<SkuInfo> pageParam = new Page<>(page, limit);
        IPage<SkuInfo> pageModel = skuInfoService.selectPage(pageParam, skuInfoQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result<SkuInfo> getById(@PathVariable Long id) {
        SkuInfo skuInfo = skuInfoService.getSkuInfo(id);
        return Result.ok(skuInfo);
    }


    @ApiOperation(value = "添加sku")
    @PostMapping("save")
    public Result insertSkuInfoVo(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.saveSkuInfoVo(skuInfoVo);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改sku")
    @PutMapping("update")
    public Result update(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.updateBySkuInfo(skuInfoVo);
        return Result.ok(skuInfoVo);
    }


    @ApiOperation(value = "删除sku")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        skuInfoService.removeById(id);
        return Result.ok(id);
    }

    @ApiOperation(value = "根据id批量删除sku")
    @DeleteMapping("batchRemove")
    public Result remove(@RequestBody List<Long> idList) {
        skuInfoService.removeByIds(idList);
        return Result.ok(idList);
    }


    @ApiOperation(value = "商品上架")
    @GetMapping("publish/{skuId}/{status}")
    public Result publish(@PathVariable Long skuId, @PathVariable Integer status) {
        skuInfoService.publish(skuId,status);
        return Result.ok(null);
    }

    @ApiOperation(value = "商品审核")
    @GetMapping("check/{skuId}/{status}")
    public Result check(@PathVariable Long skuId, @PathVariable Integer status) {
        skuInfoService.check(skuId, status);
        return Result.ok(null);
    }

    @ApiOperation(value = "新人专享")
    @GetMapping("isNewPerson/{skuId}/{status}")
    public Result isNewPerson(@PathVariable Long skuId, @PathVariable Integer status) {
        skuInfoService.isNewPerson(skuId,status);
        return Result.ok(null);
    }


}

