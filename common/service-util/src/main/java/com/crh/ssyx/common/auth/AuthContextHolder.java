package com.crh.ssyx.common.auth;

import com.crh.ssyx.vo.user.UserLoginVo;

/**
 * @program: guigu-ssyx-parent
 * @description: 获取登录用户信息类
 * @author: caoruhao
 * @create: 2023-12-06 10:50
 **/
public class AuthContextHolder {
    //会员用户id
    private static final ThreadLocal<Long> userId = new ThreadLocal<>();
    //仓库id
    private static final ThreadLocal<Long> wareId = new ThreadLocal<>();
    //会员基本信息
    private static final ThreadLocal<UserLoginVo> userLoginVo = new ThreadLocal<>();

    public static Long getUserId() {
        return userId.get();
    }

    public static void setUserId(Long _userId) {
        userId.set(_userId);
    }

    public static Long getWareId() {
        return wareId.get();
    }

    public static void setWareId(Long _wareId) {
        wareId.set(_wareId);
    }

    public static UserLoginVo getUserLoginVo() {
        return userLoginVo.get();
    }

    public static void setUserLoginVo(UserLoginVo _userLoginVo) {
        userLoginVo.set(_userLoginVo);
    }

}
