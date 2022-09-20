package com.spring2go.common.interceptor.config;

import com.spring2go.common.interceptor.chain.GlobalInterceptor;
import com.spring2go.common.interceptor.chain.InterceptingWebHandler;
import com.spring2go.common.interceptor.definition.PropertiesRouteDefinitionLocator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 拦截器自动配置
 *
 * @author xiaobin
 */
@Configuration
@EnableConfigurationProperties(InterceptorProperties.class)
public class InterceptorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator(InterceptorProperties properties) {
        return new PropertiesRouteDefinitionLocator(properties);
    }

    @Bean
    public InterceptingWebHandler interceptingWebHandler(List<GlobalInterceptor> globalInterceptors, PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator) {
        return new InterceptingWebHandler(globalInterceptors, propertiesRouteDefinitionLocator);
    }


}
