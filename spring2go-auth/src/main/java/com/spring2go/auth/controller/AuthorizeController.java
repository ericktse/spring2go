package com.spring2go.auth.controller;

import cn.hutool.core.util.RandomUtil;
import com.spring2go.auth.domain.CaptchaModel;
import com.spring2go.auth.domain.LoginModel;
import com.spring2go.auth.service.CaptchaService;
import com.spring2go.auth.service.InnerAuthorizeService;
import com.spring2go.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 认证校验Controller
 * @author: xiaobin
 * @date: 2021-05-12 10:33
 */
@Api(tags = "认证校验")
@RestController("auth")
@RequiredArgsConstructor
@Slf4j
public class AuthorizeController {

    private final InnerAuthorizeService innerAuthorizeService;
    private final CaptchaService captchaService;

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

    @ApiOperation("获取验证码")
    @GetMapping(value = "/captcha")
    public R randomImage() {
        try {
            //生成验证码
            CaptchaModel captcha = captchaService.createCaptcha();
            return R.ok(captcha);
        } catch (Exception e) {
            e.printStackTrace();

            return R.failed("获取验证码出错" + e.getMessage());
        }
    }
}
