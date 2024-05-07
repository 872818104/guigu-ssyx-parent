package com.crh.ssyx.common.exception;

import com.crh.ssyx.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * @program: guigu-ssyx-parent
 * @description:
 * @author: caoruhao
 * @create: 2023-11-01 10:37
 **/
@Data
public class SsyxException extends RuntimeException {
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    public SsyxException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public SsyxException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "Exception{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
