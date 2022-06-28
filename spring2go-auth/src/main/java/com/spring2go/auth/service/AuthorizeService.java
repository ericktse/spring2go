package com.spring2go.auth.service;


import com.spring2go.auth.domain.LoginUser;
import com.spring2go.common.core.domain.R;

/**
 * 验证服务
 *
 * @author xiaobin
 */
public interface AuthorizeService {

    R login(String username, String password);

    void logout();

    LoginUser getUserInfo();
}
