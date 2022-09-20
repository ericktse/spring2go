package com.spring2go.common.interceptor.adapter;

import com.spring2go.common.interceptor.chain.GlobalInterceptor;
import com.spring2go.common.interceptor.chain.InterceptorChain;
import com.spring2go.common.interceptor.chain.SimpleInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 默认 拦截器适配
 *
 * @author xiaobin
 */
public class DefaultInterceptorAdapter implements SimpleInterceptor {
    private final GlobalInterceptor delegate;

    public DefaultInterceptorAdapter(GlobalInterceptor delegate) {
        this.delegate = delegate;
    }

    @Override
    public void preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, InterceptorChain chain) {
        this.delegate.preHandle(httpServletRequest, httpServletResponse, chain);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, InterceptorChain chain) {
        this.delegate.postHandle(httpServletRequest, httpServletResponse, chain);
    }
}
