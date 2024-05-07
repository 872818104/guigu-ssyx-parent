package com.crh.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crh.ssyx.acl.mapper.RoleMapper;
import com.crh.ssyx.acl.service.AdminRoleService;
import com.crh.ssyx.acl.service.RoleService;
import com.crh.ssyx.model.acl.AdminRole;
import com.crh.ssyx.model.acl.Role;
import com.crh.ssyx.vo.acl.RoleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: guigu-ssyx-parent
 * @description:
 * @author: caoruhao
 * @create: 2023-11-01 17:39
 **/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private AdminRoleService adminRoleService;


    @Override
    public IPage<Role> selectPage(Page<Role> pageParam, RoleQueryVo roleQueryVo) {
        String roleName = roleQueryVo.getRoleName();
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(roleName)) {
            wrapper.eq(Role::getRoleName, roleName);
        }
        return baseMapper.selectPage(pageParam, wrapper);
    }

    //根据用户id获取用户所拥有的角色
    @Override
    public Map<String, Object> findRoleByUserId(Long adminId) {
        //1.查询所有角色
        List<Role> allRolesList = baseMapper.selectList(null);
        //2.根据用户ID查询用户分配角色列表
        //2.1根据用户id查询 用户角色关系表 admin_role,查询用户分配角色id
        //List<AdminRole>
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId, adminId);
        //设置查询条件，根据用户id adminId
        List<AdminRole> adminRoleList = adminRoleService.list(wrapper);
        //2.2通过第一步返回集合，获取所有角色列表的id List<AdminRole> ----> List<Long>
        List<Long> roleIdList = adminRoleList
                .stream()
                .map(AdminRole::getRoleId)
                .collect(Collectors.toList());
        //创建新的list集合，用于存储用户分配的角色
        List<Role> assignRoles = new ArrayList<>();
        //2.4遍历所有角色 allRolesList，得到每个角色
        //判断所有角色里面是否包含已经分配的角色id，封装到2.3里新的list集合中
        for (Role role : allRolesList) {
            if (roleIdList.contains(role.getId())){
                assignRoles.add(role);
            }
        }
        //封装到map
        Map<String, Object> roleMap = new HashMap<>();
        //所有角色
        roleMap.put("allRolesList", allRolesList);
        //用户分配角色
        roleMap.put("assignRoles",assignRoles);
        return roleMap;
    }

    //为用户分配角色
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAdminRole(Long adminId, Long[] roleIds) {
        //1.删除用户已经分配过的角色
        //根据用户id删除admin_role表的对应数据
        LambdaQueryWrapper<AdminRole> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId,adminId);
        adminRoleService.remove(wrapper);
        //2.重新分配
        //admin: 1 ==> roleId：2,3
        //遍历多个角色的id，得到每个角色id，拿着每个角色id + 用户id放到admin_role中
        List<AdminRole> list = new ArrayList<>();
        for (Long roleId:roleIds){
           AdminRole adminRole = new AdminRole();
           adminRole.setAdminId(adminId);
           adminRole.setRoleId(roleId);
           list.add(adminRole);
        }
        adminRoleService.saveBatch(list);
    }
}
