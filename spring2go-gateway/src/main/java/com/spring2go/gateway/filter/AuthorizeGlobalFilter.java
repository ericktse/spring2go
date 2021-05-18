package com.spring2go.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.spring2go.common.core.constant.AuthConstants;
import com.spring2go.common.core.constant.CommonConstants;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.ServletUtils;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.gateway.config.GatewayConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description: 全局权限过滤器
 * @author: xiaobin
 * @date: 2021-04-30 15:50
 */
@RequiredArgsConstructor
@Slf4j
public class AuthorizeGlobalFilter implements GlobalFilter, Ordered {

    private final GatewayConfigProperties gatewayConfigProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String url = exchange.getRequest().getURI().getPath();
        // 跳过不需要验证的路径
        if (StringUtils.matches(url, gatewayConfigProperties.getWhiteList())) {
            return chain.filter(exchange);
        }
        //TODO：这里仅仅只能坐Token有效性校验，没法实现授权校验（依赖shiro）,后续优化
        String token = exchange.getRequest().getHeaders().getFirst(AuthConstants.HEADER);
        if (StringUtils.isEmpty(token)) {
            return setUnauthorizedResponse(exchange, "令牌不能为空");
        }

        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return -10086;
    }


    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange, String msg) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);

        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JSON.toJSONBytes(R.unauthorized(msg)));
        }));
    }
}
