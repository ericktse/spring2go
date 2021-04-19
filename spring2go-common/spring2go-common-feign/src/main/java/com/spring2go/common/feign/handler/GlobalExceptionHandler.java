package com.spring2go.common.feign.handler;

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
    /**
     * 全局异常.
     * @param e the e
     * @return R
     */
    @ExceptionHandler(Exception.class)
    public R handleGlobalException(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);

        // 业务异常交由 sentinel 记录
        //Tracer.trace(e);
        return R.failed(e.getLocalizedMessage());
    }
}
