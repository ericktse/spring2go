package com.spring2go.common.lock.annotation;

import java.lang.annotation.*;

/**
 * @description: Redisson分布式锁注解
 * @author: xiaobin
 * @date: 2021/5/18 17:24
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DistributedLock {
    /**
     * 锁Key
     *
     * @return
     */
    String lockKey() default "";

    /**
     * 锁超时时间,默认30000毫秒
     *
     * @return int
     */
    long expireSeconds() default 30000L;

    /**
     * 等待加锁超时时间,默认10000毫秒 -1 则表示一直等待
     *
     * @return int
     */
    long waitTime() default 10000L;
}
