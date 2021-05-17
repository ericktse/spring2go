package com.spring2go.common.security.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @description: 自定义权限token
 * @author: xiaobin
 * @date: 2021-05-14 14:14
 */
public class AuthorizeToken extends UsernamePasswordToken {

    private String token;

    public AuthorizeToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
