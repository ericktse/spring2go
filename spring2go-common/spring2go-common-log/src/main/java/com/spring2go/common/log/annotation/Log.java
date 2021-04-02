package com.spring2go.common.log.annotation;

import java.lang.annotation.*;

/**
 * @Description: 系统日志注解
 * @author: xiaobin
 * @date: 2021-04-02 16:53
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 描述
     *
     * @return {String}
     */
    String value() default "";

}
