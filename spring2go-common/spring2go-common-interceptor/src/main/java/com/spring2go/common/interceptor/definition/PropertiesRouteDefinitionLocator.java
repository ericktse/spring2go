package com.spring2go.common.interceptor.definition;

import com.spring2go.common.core.util.SpringContextHolder;
import com.spring2go.common.interceptor.chain.SimpleInterceptor;
import com.spring2go.common.interceptor.config.InterceptorProperties;

import java.util.List;

/**
 * 路由定义定位器
 *
 * @author xiaobin
 */
public class PropertiesRouteDefinitionLocator {
    private final InterceptorProperties properties;
    private boolean isInit;

    public PropertiesRouteDefinitionLocator(InterceptorProperties properties) {
        this.properties = properties;
        isInit = false;

    }

    public List<RouteDefinition> getRouteDefinitions() {
        //使用时异步加载，避免强依赖spring bean加载顺序
        if (!isInit) {
            loadInterceptors();
            isInit = true;
        }

        return this.properties.getRoutes();
    }

    private void loadInterceptors() {
        this.properties.getRoutes().stream().forEach(route -> {
            route.getInterceptors().stream().forEach(interceptor -> {
                SimpleInterceptor simpleInterceptor = SpringContextHolder.getBean(interceptor.getName());
                if (null != simpleInterceptor) {
                    route.getSimpleInterceptors().add(simpleInterceptor);
                }
            });
        });
    }
}
