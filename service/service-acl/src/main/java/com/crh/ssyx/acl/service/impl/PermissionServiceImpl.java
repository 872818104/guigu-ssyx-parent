package com.crh.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crh.ssyx.acl.mapper.PermissionMapper;
import com.crh.ssyx.acl.service.PermissionService;
import com.crh.ssyx.acl.utils.PermissionHelper;
import com.crh.ssyx.model.acl.Permission;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: guigu-ssyx-parent
 * @description:
 * @author: caoruhao
 * @create: 2023-11-06 16:14
 **/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    //查询所有菜单
    @Override
    public List<Permission> queryAllPermission() {
        //1.查询所有菜单
        List<Permission> allPermissionList = baseMapper.selectList(null);
        //转换要求格式
        return PermissionHelper.buildPermission(allPermissionList);
    }

    //递归删除菜单
    @Override
    public void removeChildById(Long id) {
        //创建list集合idList 封装所有要删除的菜单id
        List<Long> idList = new ArrayList<>();
        //根据当前菜单id,获取当前菜单下的所有子菜单
        //若子菜单下面还有子菜单,则都要获取到
        //重点：递归找到当前子菜单
        this.getAllPermissionId(id, idList);
        //设置当前菜单
        idList.add(id);
        //调用方法根据多菜单ID删除
        baseMapper.deleteBatchIds(idList);
    }

    /**
     * 递归找到当前子菜单
     *
     * @param id     当前菜单id
     * @param idList 最终封装list集合 包含所有菜单id
     */
    private void getAllPermissionId(Long id, List<Long> idList) {
        //根据当前菜单id查询下面子菜单
        //select * from permission p where p.pid  = 1;
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getPid, id);
        List<Permission> childList = baseMapper.selectList(wrapper);
        //递归查询是否还有子菜单
        childList.forEach(item -> {
            idList.add(item.getId());
            this.getAllPermissionId(item.getId(), idList);
        });

    }
}
