package com.spring2go.auth.domain;

import com.spring2go.system.entity.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * login user information
 *
 * @author xiaobin
 */

/**
 * @description: 登录用户信息
 * @author: xiaobin
 * @date: 2021-05-12 14:47
 */
@Data
public class LoginUser {

    /**
     * 用户名id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 用户信息
     */
    private SysUser sysUser;
}