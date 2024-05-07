package com.crh.ssyx.sys.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crh.ssyx.model.sys.RegionWare;
import com.crh.ssyx.vo.sys.RegionWareQueryVo;


public interface RegionWareService extends IService<RegionWare> {

    IPage<RegionWare> selectPage(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo);

    void saveRegionWare(RegionWare regionWare);

    void updateStatus(Long id, Integer status);
}
