package com.crh.ssyx.product.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crh.ssyx.model.product.AttrGroup;
import com.crh.ssyx.vo.product.AttrGroupQueryVo;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author crh
 * @since 2023-11-10
 */
public interface AttrGroupService extends IService<AttrGroup> {

    IPage<AttrGroup> selectPage(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo);

    Object findAllList();
}
