package com.spring2go.common.interceptor.chain;


import com.spring2go.common.interceptor.support.InterceptorUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 自定义拦截器
 * <p>preHandle 必实现；postHandle 非必实现<p/>
 *
 * @author xiaobin
 */
public interface GlobalInterceptor {

    void preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, InterceptorChain chain);

    default void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, InterceptorChain chain) {
        chain.preHandle(httpServletRequest, httpServletResponse);
    }

    default void setParams(HttpServletRequest httpServletRequest, Map<String, Object> params) {
        httpServletRequest.setAttribute(InterceptorUtils.REQUEST_PARAMETER_NAME, params);
    }

    default Map<String, Object> getParams(HttpServletRequest httpServletRequest) {
        return (Map<String, Object>) httpServletRequest.getAttribute(InterceptorUtils.REQUEST_PARAMETER_NAME);
    }
}
