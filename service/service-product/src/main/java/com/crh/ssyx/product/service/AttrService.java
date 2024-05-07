package com.crh.ssyx.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.crh.ssyx.model.product.Attr;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author crh
 * @since 2023-11-10
 */
public interface AttrService extends IService<Attr> {

    List<Attr> getAttrListByGroupId(Long groupId);
}
