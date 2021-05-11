package com.spring2go.common.swagger.config;

import com.spring2go.common.swagger.support.SwaggerProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @description: 网关swagger 配置类，仅在webflux 环境生效哦
 * @author: xiaobin
 * @date: 2021-05-11 14:10
 */
@RequiredArgsConstructor
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class GatewaySwaggerAutoConfiguration {

    @Primary
    @Bean
    public SwaggerProvider swaggerProvider(RouteLocator routeLocator, GatewayProperties gatewayProperties) {
        return new SwaggerProvider(routeLocator, gatewayProperties);
    }
}
