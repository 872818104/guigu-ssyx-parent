package com.crh.ssyx.acl.utils;

import com.crh.ssyx.model.acl.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: guigu-ssyx-parent
 * @description: 菜单格式转换
 * @author: caoruhao
 * @create: 2023-11-08 16:33
 **/
public class PermissionHelper {
    public static List<Permission> buildPermission(List<Permission> allList) {
        List<Permission> trees = new ArrayList<>();
        for (Permission permission : allList) {
            if (permission.getPid() == 0) {
                permission.setLevel(1);
                //调用方法，从第一层开始找
                trees.add(findChildren(permission, allList));
            }
        }
        return trees;
    }

    /**
     * 递归往下找
     *
     * @param permission 当前节点
     * @param allList    下一层数据
     * @return
     */
    private static Permission findChildren(Permission permission, List<Permission> allList) {
        //遍历allList所有数据
        permission.setChildren(new ArrayList<>());
        // 判断当前节点 id = pid 是否一样
        for (Permission it : allList) {
            if (permission.getId().longValue() == it.getPid().longValue()) {
                int level = permission.getLevel() + 1;
                it.setLevel(level);
                if (permission.getChildren() == null) {
                    permission.setChildren(new ArrayList<>());
                }
                //封装下一层数据
                permission.getChildren().add(findChildren(it, allList));
            }
        }
        return permission;
    }
}
