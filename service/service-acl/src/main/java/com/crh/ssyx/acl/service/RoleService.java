package com.crh.ssyx.acl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crh.ssyx.model.acl.Role;
import com.crh.ssyx.vo.acl.RoleQueryVo;

import java.util.Map;

public interface RoleService extends IService<Role> {

    //角色分页列表
    IPage<Role> selectPage(Page<Role> pageParam, RoleQueryVo roleQueryVo);

    Map<String, Object> findRoleByUserId(Long adminId);

    void saveAdminRole(Long adminId, Long[] roleIds);
}
