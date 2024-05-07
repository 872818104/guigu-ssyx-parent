package com.crh.ssyx.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.crh.ssyx.common.auth.AuthContextHolder;
import com.crh.ssyx.common.constant.RedisConst;
import com.crh.ssyx.common.exception.SsyxException;
import com.crh.ssyx.common.result.Result;
import com.crh.ssyx.common.result.ResultCodeEnum;
import com.crh.ssyx.common.utils.JwtHelper;
import com.crh.ssyx.enums.UserType;
import com.crh.ssyx.model.user.User;
import com.crh.ssyx.user.service.UserService;
import com.crh.ssyx.user.utils.ConstantPropertiesUtil;
import com.crh.ssyx.user.utils.HttpClientUtils;
import com.crh.ssyx.vo.user.LeaderAddressVo;
import com.crh.ssyx.vo.user.UserLoginVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: guigu-ssyx-parent
 * @description:
 * @author: caoruhao
 * @create: 2023-12-04 16:48
 **/
@RestController
@RequestMapping("/api/user/weixin")
@Slf4j
public class WeiXinApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("微信登陆获取code")
    @GetMapping("/wxLogin/{code}")
    public Result wxLogin(@PathVariable String code) {
        //1.得到微信返回的临时票据
        //2.拿着code + appId + api秘钥 请求微信接口服务
        //appId
        String appId = ConstantPropertiesUtil.WX_OPEN_APP_ID;
        //api秘钥
        String appSecret = ConstantPropertiesUtil.WX_OPEN_APP_SECRET;
        log.info("微信授权服务器回调。。。。。。" + code);
        if (StringUtils.isEmpty(code)) {
            throw new SsyxException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }
        //get请求
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=%s" +
                "&secret=%s" +
                "&js_code=%s" +
                "&grant_type=authorization_code";
        String accessTokenUrl = String.format(baseAccessTokenUrl, appId, appSecret, code);
        log.info(accessTokenUrl);
        //使用HttpClient发送get请求
        String result;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            throw new SsyxException(ResultCodeEnum.FETCH_ACCESSION_FAIL);
        }
        //3.请求微信接口服务 返回两个值 session_key 和 openId
        ////openId是微信唯一标识符
        log.info("使用code换取的access_token结果 = " + result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String openId = jsonObject.getString("openid");
        String accessToken = jsonObject.getString("session_key");
        if (jsonObject.getString("errcode") != null) {
            throw new SsyxException(ResultCodeEnum.FETCH_ACCESSION_FAIL);
        }
        //4.添加用户信息到数据库中
        ////操作user表
        ////判断用户是否是第一次登陆？通过openId判断
        User user = userService.getUserByOpenId(openId);
        if (user == null) {
            user = new User();
            user.setOpenId(openId);
            user.setNickName(openId);
            user.setPhotoUrl("");
            user.setUserType(UserType.USER);
            user.setIsNew(0);
            userService.save(user);
        }
        //5.根据userID查询用户提货点和团长信息
        ////提货点：user表 user_delivery表
        ////团长：leader表
        LeaderAddressVo leaderAddressVo = userService.getLeaderAddressByUserId(user.getId());

        //6. 使用jwt，根据userId和username生成token字符串
        String token = JwtHelper.createToken(user.getId(), user.getNickName());
        //7.获取当前登陆用户信息，放到redis中，设置有效时间(重点)
        UserLoginVo userLoginVo = userService.getUserLoginVo(user.getId());
        redisTemplate.opsForValue().set(RedisConst.USER_LOGIN_KEY_PREFIX + user.getId(), userLoginVo,
                RedisConst.USERKEY_TIMEOUT, TimeUnit.DAYS);
        //8.需要封装成map数据
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("token", token);
        map.put("leaderAddressVo", leaderAddressVo);
        return Result.ok(map);
    }

    @PostMapping("/auth/updateUser")
    @ApiOperation(value = "更新用户昵称与头像")
    public Result updateUser(@RequestBody User user) {
        User user1 = userService.getById(AuthContextHolder.getUserId());
        //把昵称更新为微信用户
        user1.setNickName(user.getNickName().replaceAll("[ue000-uefff]", "*"));
        user1.setPhotoUrl(user.getPhotoUrl());
        userService.updateById(user1);
        return Result.ok(null);
    }

}
