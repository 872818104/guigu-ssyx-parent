package com.crh.ssyx.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crh.ssyx.model.user.User;
import com.crh.ssyx.vo.user.LeaderAddressVo;
import com.crh.ssyx.vo.user.UserLoginVo;

public interface UserService extends IService<User> {
    User getUserByOpenId(String openId);

    LeaderAddressVo getLeaderAddressByUserId(Long userId);

    UserLoginVo getUserLoginVo(Long userId);
}
