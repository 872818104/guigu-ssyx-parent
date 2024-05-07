package com.crh.ssyx.activity.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crh.ssyx.activity.service.ActivityInfoService;
import com.crh.ssyx.common.result.Result;
import com.crh.ssyx.model.activity.ActivityInfo;
import com.crh.ssyx.model.product.SkuInfo;
import com.crh.ssyx.vo.activity.ActivityRuleVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author crh
 * @since 2023-11-27
 */
@RestController
@RequestMapping("/admin/activity/activityInfo")
public class ActivityInfoController {

    @Autowired
    private ActivityInfoService activityInfoService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page, @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit) {
        Page<ActivityInfo> pageParam = new Page<>(page, limit);
        IPage<ActivityInfo> pageModel = activityInfoService.selectPage(pageParam);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取活动")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        ActivityInfo activityInfo = activityInfoService.getById(id);
        activityInfo.setActivityTypeString(activityInfo.getActivityType().getComment());
        return Result.ok(activityInfo);
    }


    @ApiOperation(value = "添加活动")
    @PostMapping("save")
    public Result save(@RequestBody ActivityInfo activityInfo) {
        activityInfo.setCreateTime(new Date());
        activityInfoService.save(activityInfo);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改活动")
    @PutMapping("update")
    public Result updateById(@RequestBody ActivityInfo activityInfo) {
        activityInfoService.updateById(activityInfo);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除活动")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        activityInfoService.removeById(id);
        return Result.ok(null);
    }


    //营销活动规则相关接口
    //1.根据活动id获取活动规则数据
    @ApiOperation(value = "根据活动id获取活动规则数据")
    @RequestMapping(value = "findActivityRuleList/{id}", method = RequestMethod.GET)
    public Result findActivityRuleList(@PathVariable Long id) {
        Map<String, Object> activityRuleMap = activityInfoService.findActivityRuleList(id);
        return Result.ok(activityRuleMap);
    }

    //2.在活动里面添加规则数据
    //@ApiOperation(value = "在活动里面添加规则数据")
    @PostMapping("saveActivityRule")
    public Result saveActivityRule(@RequestBody ActivityRuleVo activityRuleVo) {
        activityInfoService.saveActivityRule(activityRuleVo);
        return Result.ok(null);
    }


    //3.根据关键字查询匹配sku信息
    @GetMapping(value = "findSkuInfoByKeyword/{keyword}")
    public Result findSkuInfoByKeyword(@PathVariable("keyword") String keyword) {
        List<SkuInfo> list = activityInfoService.findSkuInfoByKeyword(keyword);
        return Result.ok(list);
    }
}

