package com.spring2go.demo.interceptor;

import com.spring2go.common.interceptor.chain.GlobalInterceptor;
import com.spring2go.common.interceptor.chain.InterceptorChain;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 拦截器Demo
 *
 * @author xiaobin
 */
@Slf4j
@Component
public class Demo2GlobalInterceptor implements GlobalInterceptor, Ordered {
    @Override
    public void preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, InterceptorChain chain) {
        log.info("------------Demo2 Interceptor preHandle------------");

        Map<String, Object> params = getParams(httpServletRequest);
        if (null != params) {
            log.info("------------Demo2 Parameter:" + params);
        }

        chain.preHandle(httpServletRequest, httpServletResponse);
    }

//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, InterceptorChain chain) {
//        log.info("------------Demo2 Interceptor postHandle------------");
//
//        //断开链路调用
//        //chain.postHandle(httpServletRequest, httpServletResponse);
//    }

    @Override
    public int getOrder() {
        return 2;
    }
}
