package com.crh.ssyx.search.service;

import com.crh.ssyx.model.search.SkuEs;

import java.util.List;

public interface SkuService {

    void upperSku(Long skuId);

    void lowerSku(Long skuId);


    List<SkuEs> findHotSkuList();
}
