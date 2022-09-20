package com.spring2go.common.interceptor.config;

import com.spring2go.common.interceptor.chain.InterceptingWebHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 拦截器配置
 *
 * @author xiaobin
 */
@Configuration
@AutoConfigureAfter(InterceptorAutoConfiguration.class)
@ConditionalOnBean(InterceptingWebHandler.class)
@RequiredArgsConstructor
public class InterceptorWebMvcConfigurer implements WebMvcConfigurer {

    private final InterceptingWebHandler interceptingWebHandler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (null != interceptingWebHandler) {
            registry.addInterceptor(interceptingWebHandler).addPathPatterns("/**");
        }
    }
}
