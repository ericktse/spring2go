package com.spring2go.common.swagger.annotation;

import com.spring2go.common.swagger.config.SwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @description: 自定义swagger
 * @author: xiaobin
 * @date: 2021/3/30 15:18
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerAutoConfiguration.class})
public @interface EnableCustomSwagger {
}
