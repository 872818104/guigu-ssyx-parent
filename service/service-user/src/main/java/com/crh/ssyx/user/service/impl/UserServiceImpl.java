package com.crh.ssyx.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crh.ssyx.model.user.Leader;
import com.crh.ssyx.model.user.User;
import com.crh.ssyx.model.user.UserDelivery;
import com.crh.ssyx.user.mapper.LeaderMapper;
import com.crh.ssyx.user.mapper.UserDeliveryMapper;
import com.crh.ssyx.user.mapper.UserMapper;
import com.crh.ssyx.user.service.UserService;
import com.crh.ssyx.vo.user.LeaderAddressVo;
import com.crh.ssyx.vo.user.UserLoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: guigu-ssyx-parent
 * @description:
 * @author: caoruhao
 * @create: 2023-12-05 09:36
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserDeliveryMapper userDeliveryMapper;

    @Autowired
    private LeaderMapper leaderMapper;

    //判断用户是否是第一次登陆？通过openId判断
    @Override
    public User getUserByOpenId(String openId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getOpenId, openId);
        return baseMapper.selectOne(wrapper);
    }

    //5.根据userId查询用户提货点和团长信息
    @Override
    public LeaderAddressVo getLeaderAddressByUserId(Long userId) {
        //根据userID默认团长id
        LambdaQueryWrapper<UserDelivery> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDelivery::getUserId, userId).eq(UserDelivery::getIsDefault, 1);
        UserDelivery userDelivery = userDeliveryMapper.selectOne(wrapper);
        if (userDelivery == null) {
            return null;
        }
        Leader leader = leaderMapper.selectById(userDelivery.getLeaderId());
        //Leader中的数据封装到LeaderAddressVo中
        LeaderAddressVo leaderAddressVo = new LeaderAddressVo();
        BeanUtils.copyProperties(leader, leaderAddressVo);
        leaderAddressVo.setLeaderId(userDelivery.getLeaderId());
        leaderAddressVo.setUserId(leader.getUserId());
        leaderAddressVo.setLeaderName(leader.getName());
        leaderAddressVo.setLeaderPhone(leader.getPhone());
        leaderAddressVo.setWareId(userDelivery.getWareId());
        leaderAddressVo.setStorePath(leader.getStorePath());
        return leaderAddressVo;
    }


    //7.获取当前登陆用户信息，放到redis中，设置有效时间
    @Override
    public UserLoginVo getUserLoginVo(Long userId) {
        User user = baseMapper.selectById(userId);
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUserId(userId);
        userLoginVo.setNickName(user.getNickName());
        userLoginVo.setPhotoUrl(user.getPhotoUrl());
        userLoginVo.setIsNew(user.getIsNew());
        userLoginVo.setOpenId(user.getOpenId());
        UserDelivery userDelivery = userDeliveryMapper.selectOne
                (new LambdaQueryWrapper<UserDelivery>()
                        .eq(UserDelivery::getUserId, userId)
                        .eq(UserDelivery::getIsDefault, 1));
        if (userDelivery != null) {
            userLoginVo.setLeaderId(userDelivery.getLeaderId());
            userLoginVo.setWareId(userDelivery.getWareId());
        } else {
            userLoginVo.setLeaderId(1L);
            userLoginVo.setWareId(1L);
        }
        return userLoginVo;
    }
}
