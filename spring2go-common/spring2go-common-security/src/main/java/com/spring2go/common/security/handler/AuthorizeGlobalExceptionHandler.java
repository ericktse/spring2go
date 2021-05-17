package com.spring2go.common.security.handler;

import com.spring2go.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @description: 权限认证全局异常处理，
 *              由于common-core中定义了全局异常处理，所以@Order设置为1，优先执行
 * @author: xiaobin
 * @date: 2021-05-14 15:16
 */
@Order(1)
@RestControllerAdvice
@Slf4j
public class AuthorizeGlobalExceptionHandler {

    @ExceptionHandler({AuthorizationException.class, UnauthorizedException.class})
    public R<?> handleException(AuthorizationException e) {

        log.error("权限异常处理：" + e.getMessage(), e);
        String message = "您的权限有误：" + e.getMessage();
        R result = R.failed(401, message);

        return result;
    }
}
