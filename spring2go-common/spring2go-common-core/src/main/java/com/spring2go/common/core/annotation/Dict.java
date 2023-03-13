package com.spring2go.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典注解
 *
 * @author xiaobin
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {

    /**
     * 数据code
     */
    String code();

    /**
     * 数据字典表
     *
     * @return 返回类型： String
     */
    String table() default "";

    /**
     * 数据字典表字段Text
     */
    String text() default "";


}
