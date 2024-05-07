package com.crh.ssyx.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crh.ssyx.common.exception.SsyxException;
import com.crh.ssyx.common.result.ResultCodeEnum;
import com.crh.ssyx.model.sys.RegionWare;
import com.crh.ssyx.sys.mapper.RegionWareMapper;
import com.crh.ssyx.sys.service.RegionWareService;
import com.crh.ssyx.vo.sys.RegionWareQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class RegionWareServiceImpl extends ServiceImpl<RegionWareMapper, RegionWare> implements RegionWareService {

    @Override
    public IPage<RegionWare> selectPage(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo) {
        String keyword = regionWareQueryVo.getKeyword();
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            //封装条件
            wrapper.like(RegionWare::getRegionName, keyword).or().like(RegionWare::getWareName, keyword);
        }
        return baseMapper.selectPage(pageParam, wrapper);
    }

    //添加开通区域
    @Override
    public void saveRegionWare(RegionWare regionWare) {
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RegionWare::getRegionId, regionWare.getRegionId());
        Long count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            //抛出异常
            throw new SsyxException(ResultCodeEnum.REGION_OPEN);
        }
        baseMapper.insert(regionWare);
    }

    //取消开通区域
    @Override
    public void updateStatus(Long id, Integer status) {
         RegionWare regionWare = baseMapper.selectById(id);
         regionWare.setStatus(status);
         baseMapper.updateById(regionWare);
    }
}
