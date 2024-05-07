package com.crh.ssyx.common.exception;

import com.crh.ssyx.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: guigu-ssyx-parent
 * @description: 统一异常处理
 * @author: caoruhao
 * @create: 2023-11-01 10:35
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SsyxException.class)
    @ResponseBody
    public Result error(SsyxException e) {
        return Result.build(null, e.getCode(), e.getMessage());
    }

}
