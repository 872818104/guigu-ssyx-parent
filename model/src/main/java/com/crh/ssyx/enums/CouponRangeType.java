package com.crh.ssyx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum CouponRangeType {
    ALL(1, "通用"),
    SKU(2, "SKU"),
    CATEGORY(3, "分类");

    @EnumValue
    private final Integer code;
    private final String comment;

    CouponRangeType(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }

}