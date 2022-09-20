package com.spring2go.common.interceptor.chain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器链
 *
 * @author xiaobin
 */
public interface InterceptorChain {
    void preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
