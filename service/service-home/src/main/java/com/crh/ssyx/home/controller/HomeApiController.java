package com.crh.ssyx.home.controller;

/*
 * @program: guigu-ssyx-parent
 * @description: 微信小程序首页接口
 * @author: caoruhao
 * @create: 2023-12-11 11:07
 **/

import com.crh.ssyx.common.auth.AuthContextHolder;
import com.crh.ssyx.common.result.Result;
import com.crh.ssyx.home.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "首页接口")
@RestController
@RequestMapping("api/home")
public class HomeApiController {

    @Autowired
    private HomeService homeService;

    @ApiOperation("获取首页数据")
    @GetMapping("index")
    public Result home() {
        Long userId = AuthContextHolder.getUserId();
        Map<String,Object> map = homeService.homeDate(userId);
        return Result.ok(map);
    }

}
