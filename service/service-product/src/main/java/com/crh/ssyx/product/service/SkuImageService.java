package com.crh.ssyx.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.crh.ssyx.model.product.SkuImage;

import java.util.List;

/**
 * <p>
 * 商品图片 服务类
 * </p>
 *
 * @author crh
 * @since 2023-11-10
 */
public interface SkuImageService extends IService<SkuImage> {

    List<SkuImage> getSkuImageById(Long id);
}
