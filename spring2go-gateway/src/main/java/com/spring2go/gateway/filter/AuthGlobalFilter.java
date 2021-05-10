package com.spring2go.gateway.filter;

import com.spring2go.common.core.util.StringUtils;
import com.spring2go.gateway.config.GatewayConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description: 全局权限过滤器
 * @author: xiaobin
 * @date: 2021-04-30 15:50
 */
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final GatewayConfigProperties gatewayConfigProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String url = exchange.getRequest().getURI().getPath();
        // 跳过不需要验证的路径
        if (StringUtils.matches(url, gatewayConfigProperties.getWhiteList())) {
            return chain.filter(exchange);
        }
        //TODO：实现token校验


        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -10086;
    }
}
