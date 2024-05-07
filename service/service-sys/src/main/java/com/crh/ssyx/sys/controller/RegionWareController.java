package com.crh.ssyx.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crh.ssyx.common.result.Result;
import com.crh.ssyx.model.sys.RegionWare;
import com.crh.ssyx.sys.service.RegionWareService;
import com.crh.ssyx.vo.sys.RegionWareQueryVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 开通区域
 *
 * @author crh
 * @since 2023-11-09
 */
@RestController
@RequestMapping("/admin/sys/regionWare")
public class RegionWareController {

    @Autowired
    private RegionWareService regionWareService;

    //开通区域列表
    @ApiOperation(value = "获取开通区域列表")
    @GetMapping("{page}/{limit}")
    public Result list(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "regionWareVo", value = "查询对象")
            RegionWareQueryVo regionWareQueryVo) {

        Page<RegionWare> pageParam = new Page<>(page, limit);
        IPage<RegionWare> pageModel = regionWareService.selectPage(pageParam, regionWareQueryVo);
        return Result.ok(pageModel);
    }

    //添加开通区域
    @ApiOperation(value = "添加开通区域")
    @PostMapping("save")
    public Result save(@RequestBody RegionWare regionWare) {
        regionWareService.saveRegionWare(regionWare);
        return Result.ok(null);
    }

    //删除开通区域
    @ApiOperation(value = "删除开通区域")
    @DeleteMapping("remove/{id}")
    public Result deleteById(@PathVariable Long id) {
        regionWareService.removeById(id);
        return Result.ok(null);
    }


    //取消开通区域
    @ApiOperation(value = "取消开通区域")
    @PostMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        regionWareService.updateStatus(id,status);
        return Result.ok(null);
    }

}

