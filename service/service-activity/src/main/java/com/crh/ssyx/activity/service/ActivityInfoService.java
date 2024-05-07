package com.crh.ssyx.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crh.ssyx.model.activity.ActivityInfo;
import com.crh.ssyx.model.product.SkuInfo;
import com.crh.ssyx.vo.activity.ActivityRuleVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动表 服务类
 * </p>
 *
 * @author crh
 * @since 2023-11-27
 */
public interface ActivityInfoService extends IService<ActivityInfo> {

    IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam);


    Map<String, Object> findActivityRuleList(Long id);


    void saveActivityRule(ActivityRuleVo activityRuleVo);

    List<SkuInfo> findSkuInfoByKeyword(String keyword);
}
