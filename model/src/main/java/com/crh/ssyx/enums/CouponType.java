package com.crh.ssyx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum CouponType {
    FULL_REDUCTION(1, "满减"),
    CASH(2, "现金卷");

    @EnumValue
    private final Integer code;
    private final String comment;

    CouponType(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }
}