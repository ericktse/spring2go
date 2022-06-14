package com.spring2go.common.rabbitmq.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 消息队列处理实例
 *
 * @author: xiaobin
 * @date: 2021-06-08 16:12
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface AmqpComponent {

    /**
     * 等同exchange名称
     */
    @AliasFor("exchangeName")
    String value() default "";

    /**
     * exchange类型
     */
    String exchangeType() default "direct";

    /**
     * exchange名称
     */
    @AliasFor("value")
    String exchangeName() default "";

    /**
     * 是否持久化
     */
    boolean durable() default true;

    /**
     * 是否自动删除
     */
    boolean autoDelete() default false;
}
