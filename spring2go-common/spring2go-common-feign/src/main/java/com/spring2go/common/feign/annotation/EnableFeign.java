package com.spring2go.common.feign.annotation;

import com.spring2go.common.feign.config.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自定义feign注解
 * 添加basePackages路径
 *
 * @author xiaobin
 * @date 2021-04-06 16:55
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
@Import({FeignAutoConfiguration.class})
public @interface EnableFeign {
    String[] value() default {};

    String[] basePackages() default {"com.spring2go"};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}
