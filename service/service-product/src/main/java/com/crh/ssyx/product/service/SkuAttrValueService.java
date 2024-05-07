package com.crh.ssyx.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.crh.ssyx.model.product.SkuAttrValue;

import java.util.List;

/**
 * <p>
 * spu属性值 服务类
 * </p>
 *
 * @author crh
 * @since 2023-11-10
 */
public interface SkuAttrValueService extends IService<SkuAttrValue> {

    List<SkuAttrValue> getSkuAttrValuesById(Long id);
}
