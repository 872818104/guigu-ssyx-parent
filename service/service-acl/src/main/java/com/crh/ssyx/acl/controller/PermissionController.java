package com.crh.ssyx.acl.controller;

import com.crh.ssyx.acl.service.PermissionService;
import com.crh.ssyx.common.result.Result;
import com.crh.ssyx.model.acl.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: guigu-ssyx-parent
 * @description: 菜单管理
 * @author: caoruhao
 * @create: 2023-11-06 16:25
 **/
@RestController
@RequestMapping("admin/acl/permission")
@Api(tags = "菜单管理")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //查询所有菜单
    @ApiOperation("查询所有菜单")
    @GetMapping
    public Result list() {
        List<Permission> permissionList = permissionService.queryAllPermission();
        return Result.ok(permissionList);
    }

    @ApiOperation("增加新的菜单")
    @PostMapping("save")
    public Result save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok(null);
    }

    @ApiOperation("修改菜单")
    @PutMapping("update")
    public Result update(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok(null);
    }

    @ApiOperation("递归删除菜单")
    @DeleteMapping("remove/{id}")
    public Result removeById(@PathVariable Long id) {
        permissionService.removeChildById(id);
        return Result.ok(null);
    }

}
