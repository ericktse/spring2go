package com.spring2go.common.amqp.annotation;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.lang.annotation.*;

/**
 * TODO
 *
 * @author: xiaobin
 * @date: 2021-06-09 10:59
 * @see RabbitListener
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@MessageMapping
@Documented
public @interface AmqpListener {
    /**
     * The unique identifier of the container managing for this endpoint.
     * <p>If none is specified an auto-generated one is provided.
     * @return the {@code id} for the container managing for this endpoint.
     * @see org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry#getListenerContainer(String)
     */
    String id() default "";


    /**
     * The queues for this listener.
     * The entries can be 'queue name', 'property-placeholder keys' or 'expressions'.
     * Expression must be resolved to the queue name or {@code Queue} object.
     * The queue(s) must exist, or be otherwise defined elsewhere as a bean(s) with
     * a {@link org.springframework.amqp.rabbit.core.RabbitAdmin} in the application
     * context.
     */
    String[] queues() default {};

}
