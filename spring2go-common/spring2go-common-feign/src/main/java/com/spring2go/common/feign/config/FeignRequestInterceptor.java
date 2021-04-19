package com.spring2go.common.feign.config;

import com.spring2go.common.core.constant.AuthConstants;
import com.spring2go.common.core.util.HeaderContextHolder;
import com.spring2go.common.core.util.IpUtils;
import com.spring2go.common.core.util.ServletUtils;
import com.spring2go.common.core.util.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description: 请求拦截器
 * @author: xiaobin
 * @date: 2021-04-16 18:06
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        //当前为主线程（同步）
        if (StringUtils.isNotNull(httpServletRequest)) {
            Map<String, String> headers = ServletUtils.getHeaders(httpServletRequest);
            // 传递用户信息请求头，防止丢失
            String authentication = headers.get(AuthConstants.AUTHORIZATION_HEADER);
            if (StringUtils.isNotEmpty(authentication)) {
                requestTemplate.header(AuthConstants.AUTHORIZATION_HEADER, authentication);

                System.out.println("auth：++++" + authentication);
            }
            // 配置客户端IP
            requestTemplate.header("X-Forwarded-For", IpUtils.getIpAddr(ServletUtils.getRequest()));
        } else { //当前为子流程(异步)
            String authentication = HeaderContextHolder.getHeader(AuthConstants.AUTHORIZATION_HEADER);
            if (StringUtils.isNotEmpty(authentication)) {
                requestTemplate.header(AuthConstants.AUTHORIZATION_HEADER, authentication);
                System.out.println("auth：----" + authentication);
            }

            //TODO:如果为子流程调用，远程地址默认为本地，后续优化
            requestTemplate.header("X-Forwarded-For", "127.0.0.1");
        }
    }
}