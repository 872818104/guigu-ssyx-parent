package com.crh.ssyx.acl.controller;

import com.crh.ssyx.common.result.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: guigu-ssyx-parent
 * @description: 登陆接口
 * @author: caoruhao
 * @create: 2023-11-01 11:12
 **/
@RestController
@RequestMapping("/admin/acl/index")
public class IndexController {

    /**
     * 1、请求登陆的login
     */
    @PostMapping("login")
    public Result login() {
        Map<String, Object> map = new HashMap<>();
        map.put("token","admin-token");
        return Result.ok(map);
    }

    /**
     * 2 获取用户信息
     */
    @GetMapping("info")
    public Result info() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "crh");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

    /**
     * 3 退出
     */
    @PostMapping("logout")
    public Result logout() {
        return Result.ok(null);
    }
}
