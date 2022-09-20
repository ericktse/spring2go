package com.spring2go.demo.interceptor;

import com.spring2go.common.interceptor.chain.GlobalInterceptor;
import com.spring2go.common.interceptor.chain.InterceptorChain;
import com.spring2go.common.interceptor.chain.SimpleInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截器Demo
 *
 * @author xiaobin
 */
@Slf4j
@Component
public class Demo3Interceptor implements SimpleInterceptor, Ordered {
    @Override
    public void preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, InterceptorChain chain) {
        log.info("------------Demo3 simple Interceptor preHandle------------");
        chain.preHandle(httpServletRequest, httpServletResponse);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, InterceptorChain chain) {
        log.info("------------Demo3 simple Interceptor postHandle------------");

        chain.postHandle(httpServletRequest, httpServletResponse);
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
