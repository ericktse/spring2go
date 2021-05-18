package com.spring2go.common.security.annotation;

import java.lang.annotation.*;

/**
 * @description: 服务调用不鉴权注解
 * @author: xiaobin
 * @date: 2021/5/18 9:35
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inner {
    /**
     * 是否AOP统一处理
     *
     * @return false, true
     */
    boolean value() default true;
}
