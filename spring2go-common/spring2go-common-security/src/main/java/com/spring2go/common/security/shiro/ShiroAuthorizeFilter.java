package com.spring2go.common.security.shiro;

import com.alibaba.fastjson.JSON;
import com.spring2go.common.core.constant.AuthConstants;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
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
        String token = getToken(request);
        if (token == null) {
            throw new UnauthorizedException("token非法无效");
        }

        return new AuthorizeToken(token);
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //Always return true if the request's method is OPTIONS
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }

        //token不存在則返回失敗
        String token = getToken(request);
        if (token == null) {
            return false;
        }

        try {
            return executeLogin(request, response);
        } catch (Exception e) {
            throw new AuthenticationException("Token失效，请重新登录", e);
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", "true");
        httpResponse.setCharacterEncoding("UTF-8");
        String json = JSON.toJSONString(R.failed(401, "token非法无效，请重新登录"));
        httpResponse.getWriter().print(json);
        return false;
    }

    /**
     * 对跨域提供支持
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
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


    /**
     * @description 获取Token
     * @author xiaobin
     * @date 2021/5/17 11:38
     */
    private String getToken(ServletRequest request) {
        //获取请求token
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(AuthConstants.HEADER);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isEmpty(token)) {
            token = httpServletRequest.getParameter(AuthConstants.HEADER);
        }

        return token;
    }
}
