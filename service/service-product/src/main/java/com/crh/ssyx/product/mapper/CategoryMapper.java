package com.crh.ssyx.product.mapper;

import com.crh.ssyx.model.product.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 商品三级分类 Mapper 接口
 * </p>
 *
 * @author crh
 * @since 2023-11-10
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
