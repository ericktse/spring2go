package com.spring2go.auth.controller;

import com.spring2go.auth.domain.LoginModel;
import com.spring2go.auth.service.InnerAuthorizeService;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.redis.util.RedisUtils;
import com.spring2go.common.security.util.TokenUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @description: 认证校验Controller
 * @author: xiaobin
 * @date: 2021-05-12 10:33
 */
@Api(tags = "认证校验")
@RestController("auth/")
@RequiredArgsConstructor
@Slf4j
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
        innerAuthorizeService.logout();
        return R.ok();
    }
}
