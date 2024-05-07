package com.crh.ssyx.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crh.ssyx.model.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
