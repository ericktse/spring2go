package com.spring2go.common.interceptor.predicate;

import com.spring2go.common.interceptor.definition.RouteDefinition;

import javax.servlet.http.HttpServletRequest;

/**
 * 拦截器断言
 *
 * @author xiaobin
 */
public interface InterceptorPredicate {
    boolean apply(HttpServletRequest httpServletRequest, RouteDefinition route);
}
