package com.crh.ssyx.acl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.crh.ssyx.acl.service.AdminService;
import com.crh.ssyx.acl.service.RoleService;
import com.crh.ssyx.common.result.Result;
import com.crh.ssyx.common.uitls.MD5;
import com.crh.ssyx.model.acl.Admin;
import com.crh.ssyx.vo.acl.AdminQueryVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * @program: guigu-ssyx-parent
 * @description: 用户管理
 * @author: caoruhao
 * @create: 2023-11-02 10:18
 **/
@RestController
@RequestMapping("admin/acl/user")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public Result adminList(@ApiParam(name = "page", value = "当前页码", required = true)
                            @PathVariable Long page,
                            @ApiParam(name = "limit", value = "每页记录数", required = true)
                            @PathVariable Long limit,
                            @ApiParam(name = "userQueryVo", value = "查询对象")
                            AdminQueryVo adminQueryVo) {
        Page<Admin> pageParam = new Page<>(page, limit);
        IPage<Admin> pageModel = adminService.selectPage(pageParam, adminQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取用户")
    @GetMapping("get/{id}")
    public Result getAdminById(@PathVariable Long id) {
        Admin admin = adminService.getById(id);
        return Result.ok(admin); 
    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public Result save(@RequestBody Admin admin) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //对密码进行MD5处理
        admin.setPassword(MD5.getEncryptedPwd(admin.getPassword()));
        adminService.save(admin);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改管理用户")
    @PutMapping("update")
    public Result updateById(@RequestBody Admin user) {
        adminService.updateById(user);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        adminService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        adminService.removeByIds(idList);
        return Result.ok(null);
    }

    @ApiOperation(value = "获取角色数据")
    @GetMapping("/toAssign/{adminId}")
    public Result toAssign(@PathVariable Long adminId) {
        //返回数据包含两部分：所有角色和为用户分配角色
        Map<String, Object> roleMap = roleService.findRoleByUserId(adminId);
        return Result.ok(roleMap);
    }

    @ApiOperation(value = "为用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestParam Long adminId,
                           @RequestParam Long[] roleIds) {
        roleService.saveAdminRole(adminId,roleIds);
        return Result.ok(null);
    }

}
