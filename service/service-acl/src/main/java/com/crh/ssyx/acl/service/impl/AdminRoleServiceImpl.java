package com.crh.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crh.ssyx.acl.mapper.AdminRoleMapper;
import com.crh.ssyx.acl.service.AdminRoleService;
import com.crh.ssyx.model.acl.AdminRole;
import org.springframework.stereotype.Service;

/**
 * @program: guigu-ssyx-parent
 * @description: 给用户分配角色
 * @author: caoruhao
 * @create: 2023-11-02 11:35
 **/
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole>
        implements AdminRoleService {

}
