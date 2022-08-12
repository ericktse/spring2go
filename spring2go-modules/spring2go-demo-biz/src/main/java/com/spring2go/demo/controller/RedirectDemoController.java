package com.spring2go.demo.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 重定向
 *
 * @author xiaobin
 */
@Api(tags = "Redirect示例")
@RestController
public class RedirectDemoController {

    @GetMapping("/redirect")
    String redirect(HttpServletResponse response) throws IOException {
        Cookie tokenCookie = new Cookie("cookie-name", "cookie");
        tokenCookie.setDomain("www.baidu.com");
        response.addCookie(tokenCookie);
        response.setHeader("Location", "www.baidu.com");
        response.setHeader("token", "mytoken");
        String redirectUrl = "https://www.baidu.com";
        return "redirect:" + redirectUrl;
    }
}
