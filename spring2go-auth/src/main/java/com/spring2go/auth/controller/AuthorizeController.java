package com.spring2go.auth.controller;

import com.spring2go.auth.domain.CaptchaModel;
import com.spring2go.auth.domain.LoginModel;
import com.spring2go.auth.domain.LoginUser;
import com.spring2go.auth.service.AuthorizeService;
import com.spring2go.auth.service.CaptchaService;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.log.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 认证校验Controller
 * @author: xiaobin
 * @date: 2021-05-12 10:33
 */
@Api(tags = "认证校验")
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
public class AuthorizeController {

    private final AuthorizeService authorizeService;
    private final CaptchaService captchaService;

    @Log("登录")
    @ApiOperation("登录")
    @PostMapping("login")
    public R<?> login(@RequestBody LoginModel model) {

        // 校验 用户名和密码
        if (null == model || StringUtils.isEmpty(model.getUsername()) || StringUtils.isEmpty(model.getPassword())) {
            return R.failed("用户/密码必须填写");
        }

        //校验 验证码
//        if (!captchaService.checkCaptcha(model.getKey(), model.getCode())) {
//            return R.failed("验证码错误");
//        }

        // 用户登录
        R result = authorizeService.login(model.getUsername(), model.getPassword());
        return result;
    }

    @Log("登出")
    @ApiOperation("登出")
    @PostMapping("logout")
    public R<?> logout(HttpServletRequest request) {
        authorizeService.logout();
        return R.ok();
    }

    @Log("获取当前登录用户信息")
    @ApiOperation("获取当前登录用户信息")
    @GetMapping(value = "/getUserInfo")
    public R getUserInfo() {
        LoginUser user = authorizeService.getUserInfo();
        if (null == user) {
            return R.failed("获取当前登录用户信息出错");
        }
        return R.ok(user);
    }

    @Log("获取验证码")
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
