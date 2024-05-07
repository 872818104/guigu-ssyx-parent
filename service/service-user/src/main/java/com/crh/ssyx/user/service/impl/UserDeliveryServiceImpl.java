package com.crh.ssyx.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crh.ssyx.model.user.UserDelivery;
import com.crh.ssyx.user.mapper.UserDeliveryMapper;
import com.crh.ssyx.user.service.UserDeliveryService;
import org.springframework.stereotype.Service;

/**
 * @program: guigu-ssyx-parent
 * @description:
 * @author: caoruhao
 * @create: 2023-12-05 15:07
 **/
@Service
public class UserDeliveryServiceImpl extends
        ServiceImpl<UserDeliveryMapper, UserDelivery> implements UserDeliveryService {
}
