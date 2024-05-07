package com.crh.ssyx.user.api;

import com.crh.ssyx.user.service.UserService;
import com.crh.ssyx.vo.user.LeaderAddressVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: guigu-ssyx-parent
 * @description: 远程调用接口
 * @author: caoruhao
 * @create: 2023-12-11 11:30
 **/
@RestController
@RequestMapping(value = "/api/user/leader")
public class LeaderAddressApiController {

    @Autowired
    private UserService userService;

    @ApiOperation("提货点地址信息")
    @GetMapping("/inner/getUserAddressByUserId/{userId}")
    public LeaderAddressVo getUserAddressByUserId(@PathVariable("userId") Long userId) {
        return userService.getLeaderAddressByUserId(userId);
    }    
}
