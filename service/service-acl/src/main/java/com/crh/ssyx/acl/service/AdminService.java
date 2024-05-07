package com.crh.ssyx.acl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crh.ssyx.model.acl.Admin;
import com.crh.ssyx.vo.acl.AdminQueryVo;

public interface AdminService extends IService<Admin> {
    IPage<Admin> selectPage(Page<Admin> pageParam, AdminQueryVo adminQueryVo);
}
