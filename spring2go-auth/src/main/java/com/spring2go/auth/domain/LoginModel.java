package com.spring2go.auth.domain;

import lombok.Data;

/**
 * @description: 登录对象
 * @author: xiaobin
 * @date: 2021-05-12 10:35
 */
@Data
public class LoginModel {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;
}
