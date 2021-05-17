package com.spring2go.common.security.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @description: 安全校验用户保证
 * @author: xiaobin
 * @date: 2021-05-13 15:27
 */
@Data
public class SimpleAuthorizeUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private Object originalUser;
    private Set<String> roles;
    private Set<String> permissions;

    public SimpleAuthorizeUser(String username, Object originalUser, Set<String> roles, Set<String> permissions) {
        this.username=username;
        this.originalUser=originalUser;
        this.roles=roles;
        this.permissions=permissions;
    }
}
