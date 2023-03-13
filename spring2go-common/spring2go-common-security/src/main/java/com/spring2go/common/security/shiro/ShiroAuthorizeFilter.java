package com.spring2go.common.security.shiro;

import com.alibaba.fastjson2.JSON;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.security.util.TokenUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 自定义权限过滤器
 * @author: xiaobin
 * @date: 2021-05-14 11:44
 */
public class ShiroAuthorizeFilter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String token = TokenUtils.getTokenByRequest((HttpServletRequest) request);
        if (token == null) {
            throw new UnauthorizedException("token非法无效，请重新登录");
        }

        return new AuthorizeToken(token);
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // OPTIONS方法直接返回true
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }

        //token不存在則返回失敗
        String token = TokenUtils.getTokenByRequest((HttpServletRequest) request);
        if (token == null) {
            return false;
        }

        try {
            return executeLogin(request, response);
        } catch (Exception e) {
            throw new UnauthorizedException("token非法无效，请重新登录", e);
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", "true");
        httpResponse.setHeader("Content-Type", "text/html;charset=UTF-8");
        httpResponse.setCharacterEncoding("UTF-8");
        String json = JSON.toJSONString(R.unauthorized("token非法无效，请重新登录"));
        httpResponse.getWriter().print(json);
        return false;
    }

    /**
     * @description: 对跨域提供支持
     * @author: xiaobin
     * @date: 2021/5/17 13:44
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

}
