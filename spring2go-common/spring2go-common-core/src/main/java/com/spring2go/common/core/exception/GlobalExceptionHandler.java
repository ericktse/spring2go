package com.spring2go.common.core.exception;

import com.spring2go.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @description: 全局异常处理
 * @author: xiaobin
 * @date: 2021-04-16 18:08
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R<?> handleException(Exception e) {
        log.error("全局异常处理：" + e.getMessage(), e);
        return R.failed("操作异常：" + e.getMessage());
    }
}
