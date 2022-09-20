package com.spring2go.demo.interceptor;

import com.spring2go.common.interceptor.chain.GlobalInterceptor;
import com.spring2go.common.interceptor.chain.InterceptorChain;
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
public class Demo1GlobalInterceptor implements GlobalInterceptor, Ordered {
    @Override
    public void preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, InterceptorChain chain) {
        log.info("------------Demo1 Interceptor preHandle------------");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "foo");
        params.put("age", 18);

        setParams(httpServletRequest, params);

        chain.preHandle(httpServletRequest, httpServletResponse);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, InterceptorChain chain) {
        log.info("------------Demo1 Interceptor postHandle------------");

        chain.postHandle(httpServletRequest, httpServletResponse);

        //postHandle是在response之后执行，无法修改response，需通过Responsebodyadvice实现修改
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
