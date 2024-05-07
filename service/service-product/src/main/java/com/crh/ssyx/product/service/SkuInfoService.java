package com.crh.ssyx.product.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crh.ssyx.model.product.SkuInfo;
import com.crh.ssyx.vo.product.SkuInfoQueryVo;
import com.crh.ssyx.vo.product.SkuInfoVo;

import java.util.List;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author crh
 * @since 2023-11-10
 */
public interface SkuInfoService extends IService<SkuInfo> {

    IPage<SkuInfo> selectPage(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo);

    void saveSkuInfoVo(SkuInfoVo skuInfoVo);

    void updateBySkuInfo(SkuInfoVo skuInfoVo);

    SkuInfo getSkuInfo(Long id);

    void publish(Long skuId, Integer status);

    void isNewPerson(Long skuId, Integer status);

    void check(Long skuId, Integer status);

    List<SkuInfo> findSkuInfoList(List<Long> skuIdLists);

    List<SkuInfo> findSkuInfoByKeyword(String keyword);


    List<SkuInfo> findNewPersonSkuInfoList();
}
