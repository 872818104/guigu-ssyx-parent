package com.crh.ssyx.home.service.impl;

import com.crh.ssyx.client.product.ProductFeignClient;
import com.crh.ssyx.client.search.SkuFeignClient;
import com.crh.ssyx.client.user.UserFeignClient;
import com.crh.ssyx.home.service.HomeService;
import com.crh.ssyx.model.product.Category;
import com.crh.ssyx.model.product.SkuInfo;
import com.crh.ssyx.model.search.SkuEs;
import com.crh.ssyx.vo.user.LeaderAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: guigu-ssyx-parent
 * @description:
 * @author: caoruhao
 * @create: 2023-12-11 11:09
 **/
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private SkuFeignClient skuFeignClient;


    @Override
    public Map<String, Object> homeDate(Long userId) {

        Map<String, Object> map = new HashMap<>();
        //1.根据userId获取当前登录用户提货信息
        //远程调用service-user中的登陆接口
        LeaderAddressVo leaderAddressVo = userFeignClient.getUserAddressByUserId(userId);
        map.put("leaderAddressVo", leaderAddressVo);
        //2.获取所有分类
        //远程调用service-product
        List<Category> categoryList = productFeignClient.findAllCategoryList();
        map.put("categoryList", categoryList);
        //3.获取新人专享
        //远程调用service-product
        List<SkuInfo> newPersonSkuInfoList = productFeignClient.findNewPersonSkuInfoList();
        map.put("newPersonSkuInfoList", newPersonSkuInfoList);
        //获取爆款商品
        //远程调用service-search
        //score 热门评分降序排序
        List<SkuEs> hotSkuList = skuFeignClient.findHotSkuList();
        map.put("hotSkuList",hotSkuList);
        //返回封装过的结果
        return map;
    }
}
