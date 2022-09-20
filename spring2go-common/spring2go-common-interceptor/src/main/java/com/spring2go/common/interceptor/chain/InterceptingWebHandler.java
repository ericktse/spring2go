package com.spring2go.common.interceptor.chain;

import com.spring2go.common.interceptor.adapter.DefaultInterceptorAdapter;
import com.spring2go.common.interceptor.adapter.OrderedInterceptorAdapter;
import com.spring2go.common.interceptor.definition.PropertiesRouteDefinitionLocator;
import com.spring2go.common.interceptor.definition.RouteDefinition;
import com.spring2go.common.interceptor.predicate.PathRoutePredicate;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Web Handler Interceptor that delegates to a chain of {@link GlobalInterceptor} instances and {@link SimpleInterceptor} instances
 *
 * @author xiaobin
 */
public class InterceptingWebHandler implements HandlerInterceptor {

    private final List<SimpleInterceptor> globalInterceptors;
    private final PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator;

    public InterceptingWebHandler(List<GlobalInterceptor> globalInterceptors, PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator) {
        this.globalInterceptors = loadInterceptors(globalInterceptors);
        this.propertiesRouteDefinitionLocator = propertiesRouteDefinitionLocator;
    }

    private static List<SimpleInterceptor> loadInterceptors(List<GlobalInterceptor> interceptors) {
        return interceptors.stream().map(interceptor -> {
            DefaultInterceptorAdapter interceptorAdapter = new DefaultInterceptorAdapter(interceptor);
            if (interceptor instanceof Ordered) {
                int order = ((Ordered) interceptor).getOrder();
                return new OrderedInterceptorAdapter(interceptorAdapter, order);
            }
            return interceptorAdapter;
        }).collect(Collectors.toList());
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        List<SimpleInterceptor> combined = combinedInterceptors(request);

        DefaultInterceptorChain defaultInterceptorChain = new DefaultInterceptorChain(combined);
        defaultInterceptorChain.preHandle(request, response);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

        List<SimpleInterceptor> combined = combinedInterceptors(request);
        //倒序执行
        Collections.reverse(combined);

        DefaultInterceptorChain defaultInterceptorChain = new DefaultInterceptorChain(combined);
        defaultInterceptorChain.postHandle(request, response);

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    private List<SimpleInterceptor> combinedInterceptors(HttpServletRequest request) {
        List<SimpleInterceptor> combined = new ArrayList<>(this.globalInterceptors);

        List<RouteDefinition> routeDefinitions = this.propertiesRouteDefinitionLocator.getRouteDefinitions();
        PathRoutePredicate predicate = new PathRoutePredicate();
        routeDefinitions.stream().forEach(routeDefinition -> {
            if (predicate.apply(request, routeDefinition)) {
                combined.addAll(routeDefinition.getSimpleInterceptors());
            }
            AnnotationAwareOrderComparator.sort(combined);
        });

        return combined;
    }
}
