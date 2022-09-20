package com.spring2go.common.interceptor.chain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 默认拦截器链
 *
 * @author xiaobin
 */
public class DefaultInterceptorChain implements InterceptorChain {
    private final int index;

    private final List<SimpleInterceptor> interceptors;

    DefaultInterceptorChain(List<SimpleInterceptor> filters) {
        this.interceptors = filters;
        this.index = 0;
    }

    private DefaultInterceptorChain(DefaultInterceptorChain parent, int index) {
        this.interceptors = parent.getInterceptors();
        this.index = index;
    }

    public List<SimpleInterceptor> getInterceptors() {
        return interceptors;
    }


    @Override
    public void preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (this.index < interceptors.size()) {
            SimpleInterceptor interceptor = interceptors.get(this.index);
            DefaultInterceptorChain chain = new DefaultInterceptorChain(this, this.index + 1);
            interceptor.preHandle(httpServletRequest, httpServletResponse, chain);
        } else {
            return; // complete
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (this.index < interceptors.size()) {
            SimpleInterceptor interceptor = interceptors.get(this.index);
            DefaultInterceptorChain chain = new DefaultInterceptorChain(this, this.index + 1);
            interceptor.postHandle(httpServletRequest, httpServletResponse, chain);
        } else {
            return; // complete
        }
    }
}
