package com.spring2go.demo.interceptor;

import com.spring2go.common.interceptor.chain.InterceptorChain;
import com.spring2go.common.interceptor.chain.SimpleInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器Demo
 *
 * @author xiaobin
 */
@Slf4j
@Component
public class Demo4Interceptor implements SimpleInterceptor, Ordered {
    @Override
    public void preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, InterceptorChain chain) {
        log.info("------------Demo4 simple Interceptor preHandle------------");
        chain.preHandle(httpServletRequest, httpServletResponse);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, InterceptorChain chain) {
        log.info("------------Demo4 simple Interceptor postHandle------------");

        chain.postHandle(httpServletRequest, httpServletResponse);
    }

    @Override
    public int getOrder() {
        return 4;
    }
}
