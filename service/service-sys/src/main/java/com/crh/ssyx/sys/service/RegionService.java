package com.crh.ssyx.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.crh.ssyx.model.sys.Region;

import java.util.List;

/**
 * <p>
 * 地区表 服务类
 * </p>
 *
 * @author crh
 * @since 2023-11-09
 */
public interface RegionService extends IService<Region> {

    List<Region> findRegionByKeyword(String keyword);
}
