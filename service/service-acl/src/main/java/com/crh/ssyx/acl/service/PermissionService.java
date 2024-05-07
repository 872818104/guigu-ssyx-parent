package com.crh.ssyx.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crh.ssyx.model.acl.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    List<Permission> queryAllPermission();

    void removeChildById(Long id);
}
