package com.spring2go.common.security.aspect;

import com.spring2go.common.core.constant.SecurityConstants;
import com.spring2go.common.core.util.ServletUtils;
import com.spring2go.common.security.annotation.Inner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;

import java.nio.file.AccessDeniedException;

/**
 * @description: 服务间接口不鉴权处理逻辑
 * @author: xiaobin
 * @date: 2021-05-18 9:37
 */
@Aspect
@Slf4j
public class InnerAspect implements Ordered {

    @Around("@annotation(inner)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, Inner inner) {
        //实际注入的inner实体由表达式后一个注解决定，即是方法上的@Inner注解实体，若方法上无@Inner注解，则获取类上的
        if (inner == null) {
            Class<?> clazz = point.getTarget().getClass();
            inner = AnnotationUtils.findAnnotation(clazz, Inner.class);
        }
        String header = ServletUtils.getHeader(SecurityConstants.FROM);
        if (inner.value() && !SecurityConstants.FROM_IN.equals(header)) {
            log.warn("访问接口 {} 没有权限", point.getSignature().getName());
            throw new AccessDeniedException("Access is denied");
        }
        return point.proceed();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
