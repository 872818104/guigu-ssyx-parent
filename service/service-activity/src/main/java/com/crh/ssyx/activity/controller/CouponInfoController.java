package com.crh.ssyx.activity.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.crh.ssyx.activity.service.CouponInfoService;
import com.crh.ssyx.common.result.Result;
import com.crh.ssyx.model.activity.CouponInfo;
import com.crh.ssyx.vo.activity.CouponRuleVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 优惠券信息
 *
 * @author crh
 * @since 2023-11-27
 */
@RestController
@RequestMapping("/admin/activity/couponInfo")
public class CouponInfoController {

    @Autowired
    private CouponInfoService couponInfoService;

    //优惠券分页查询
    @GetMapping("{page}/{limit}")
    public Result getPageList(@PathVariable Long page, @PathVariable Long limit) {
        IPage<CouponInfo> couponInfoPage = couponInfoService.selectCouponInfo(page, limit);
        return Result.ok(couponInfoPage);
    }

    //添加优惠券
    @ApiOperation("添加优惠券")
    @PostMapping("save")
    public Result save(@RequestBody CouponInfo couponInfo) {
        couponInfoService.save(couponInfo);
        return Result.ok(null);
    }

    //修改优惠券
    @ApiOperation("修改优惠券")
    @PutMapping("update")
    public Result updateById(@RequestBody CouponInfo couponInfo) {
        couponInfoService.updateById(couponInfo);
        return Result.ok(null);
    }


    @ApiOperation(value = "删除优惠券")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        couponInfoService.removeById(id);
        return Result.ok(null);
    }

    //根据id查询优惠券
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        CouponInfo couponInfo = couponInfoService.getCouponInfo(id);
        return Result.ok(couponInfo);
    }

    //根据优惠券id查询规则信息
    @RequestMapping(value = "findCouponRuleList/{id}", method = RequestMethod.GET)
    public Result findCouponRuleList(@PathVariable Long id) {
        Map<String, Object> couponInfoList = couponInfoService.findCouponRuleList(id);
        return Result.ok(couponInfoList);
    }

    //添加优惠券规则数据
    @PostMapping("saveCouponRule")
    public Result saveCouponRule(@RequestBody CouponRuleVo couponRuleVo) {
        couponInfoService.saveCouponRule(couponRuleVo);
        return Result.ok(null);
    }
}

