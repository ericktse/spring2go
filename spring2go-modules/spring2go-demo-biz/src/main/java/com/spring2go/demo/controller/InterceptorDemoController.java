package com.spring2go.demo.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: Config示例代码
 * @author: xiaobin
 * @date: 2021-04-13 10:00
 */
@Api(tags = "Interceptor示例")
@RestController
@Slf4j
public class InterceptorDemoController {

    @RequestMapping("/interceptor/get")
    public String get(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        log.info("exec interceptor,params : " + httpServletRequest.getAttribute("params"));
        return "success";
    }

}
