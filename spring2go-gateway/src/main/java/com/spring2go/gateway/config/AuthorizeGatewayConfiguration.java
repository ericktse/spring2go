package com.spring2go.gateway.config;

import com.spring2go.gateway.filter.AuthorizeGlobalFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 权限认证配置
 * @author: xiaobin
 * @date: 2021-05-07 11:31
 */
@Configuration
public class AuthorizeGatewayConfiguration {

    @Bean
    public GlobalFilter authorizeGlobalFilter(GatewayConfigProperties gatewayConfigProperties) {
        return new AuthorizeGlobalFilter(gatewayConfigProperties);
    }
}
