package com.spring2go.common.interceptor.adapter;

import com.spring2go.common.interceptor.chain.GlobalInterceptor;
import com.spring2go.common.interceptor.chain.InterceptorChain;
import com.spring2go.common.interceptor.chain.SimpleInterceptor;
import org.springframework.core.Ordered;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Order 拦截器适配
 *
 * @author xiaobin
 */
public class OrderedInterceptorAdapter implements SimpleInterceptor, Ordered {
    private final SimpleInterceptor delegate;

    private final int order;

    public OrderedInterceptorAdapter(SimpleInterceptor delegate, int order) {
        this.delegate = delegate;
        this.order = order;
    }


    @Override
    public void preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, InterceptorChain chain) {
        this.delegate.preHandle(httpServletRequest, httpServletResponse, chain);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, InterceptorChain chain) {
        this.delegate.postHandle(httpServletRequest, httpServletResponse, chain);
    }

    @Override
    public int getOrder() {
        return this.order;
    }

}
