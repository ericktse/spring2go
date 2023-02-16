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

    @GetMapping("/redirect1")
    String redirect1(HttpServletResponse response) throws IOException {
        Cookie tokenCookie = new Cookie("cookie-name", "cookie");
        tokenCookie.setDomain("www.baidu.com");
        response.addCookie(tokenCookie);
        response.setHeader("Location", "www.baidu.com");
        response.setHeader("token", "mytoken");
        response.setStatus(HttpServletResponse.SC_FOUND);
        String redirectUrl = "https://www.baidu.com?token=aaa";
        //需要根据链接重定向
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/redirect2")
    void redirect2(HttpServletResponse response) throws IOException {
        //重定向不会带上cookies
        Cookie tokenCookie = new Cookie("cookie-name", "cookie");
        response.addCookie(tokenCookie);
        String redirectUrl = "https://www.baidu.com?token=xxx";
        //浏览器自动重定向
        response.sendRedirect(redirectUrl);
    }
}
