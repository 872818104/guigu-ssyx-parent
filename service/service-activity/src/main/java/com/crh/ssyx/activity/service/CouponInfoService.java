package com.crh.ssyx.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.crh.ssyx.model.activity.CouponInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crh.ssyx.vo.activity.CouponRuleVo;

import java.util.Map;

/**
 * <p>
 * 优惠券信息 服务类
 * </p>
 *
 * @author crh
 * @since 2023-11-27
 */
public interface CouponInfoService extends IService<CouponInfo> {

    IPage<CouponInfo> selectCouponInfo(Long page, Long limit);

    CouponInfo getCouponInfo(Long id);

    Map<String, Object> findCouponRuleList(Long id);

    void saveCouponRule(CouponRuleVo couponRuleVo);
}
