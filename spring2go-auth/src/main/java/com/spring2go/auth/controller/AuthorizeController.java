package com.spring2go.auth.controller;

import com.spring2go.auth.domain.LoginModel;
import com.spring2go.auth.service.InnerAuthorizeService;
import com.spring2go.common.core.domain.R;
import com.spring2go.upms.api.entity.SysUser;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 认证校验Controller
 * @author: xiaobin
 * @date: 2021-05-12 10:33
 */
@Api(tags = "认证校验")
@RestController
@RequiredArgsConstructor
public class AuthorizeController {

    private final InnerAuthorizeService innerAuthorizeService;

    @PostMapping("login")
    public R<?> login(@RequestBody LoginModel model) {
        // 用户登录
        R result = innerAuthorizeService.login(model.getUsername(), model.getPassword());
        return result;
    }

    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request) {
//        LoginUser loginUser = tokenService.getLoginUser(request);
//        innerAuthorizeService.logout(loginUser);
        return R.ok();
    }
}
