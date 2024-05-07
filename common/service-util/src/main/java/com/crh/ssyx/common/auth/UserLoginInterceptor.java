package com.crh.ssyx.common.auth;


import com.crh.ssyx.common.constant.RedisConst;
import com.crh.ssyx.common.utils.JwtHelper;
import com.crh.ssyx.vo.user.UserLoginVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: guigu-ssyx-parent
 * @description:
 * @author: caoruhao
 * @create: 2023-12-06 10:58
 **/
//@SuppressWarnings("all")
public class UserLoginInterceptor implements HandlerInterceptor {

    private final RedisTemplate redisTemplate;

    public UserLoginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //preHandle：在业务处理器处理请求之前被调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.getUserLoginVo(request);
        return true;
    }

    private void getUserLoginVo(HttpServletRequest request) {
        //1.从请求头中获取token
        String token = request.getHeader("token");
        if (!StringUtils.isEmpty(token)) {
            //从token中获取用户id
            Long userId = JwtHelper.getUserId(token);
            //根据userId获取redis中用户信息
            UserLoginVo userLoginVo = (UserLoginVo) redisTemplate.opsForValue().get(RedisConst.USER_LOGIN_KEY_PREFIX + userId);
            //数据放到ThreadLocal
            if (userLoginVo != null) {
                AuthContextHolder.setUserId(userLoginVo.getUserId());
                AuthContextHolder.setWareId(userLoginVo.getWareId());
                AuthContextHolder.setUserLoginVo(userLoginVo);
            }
        }

    }
}
