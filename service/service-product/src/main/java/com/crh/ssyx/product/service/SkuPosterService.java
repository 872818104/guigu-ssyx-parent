package com.crh.ssyx.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.crh.ssyx.model.product.SkuPoster;

import java.util.List;

/**
 * <p>
 * 商品海报表 服务类
 * </p>
 *
 * @author crh
 * @since 2023-11-10
 */
public interface SkuPosterService extends IService<SkuPoster> {

    List<SkuPoster> getSkuPosterById(Long id);
}
