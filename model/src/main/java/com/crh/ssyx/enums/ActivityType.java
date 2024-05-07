package com.crh.ssyx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum ActivityType {
    FULL_REDUCTION(1, "满减"),
    FULL_DISCOUNT(2, "满量打折");

    @EnumValue
    private final Integer code;
    private final String comment;

    ActivityType(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }
}