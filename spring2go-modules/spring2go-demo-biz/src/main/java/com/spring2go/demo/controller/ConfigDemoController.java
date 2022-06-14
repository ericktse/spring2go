package com.spring2go.demo.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: Config示例代码
 * @author: xiaobin
 * @date: 2021-04-13 10:00
 */
@Api(tags = "Config示例")
@RestController
@RefreshScope // 动态刷新nacos配置。RefreshScope与RestTemplate存在嵌套引用
public class ConfigDemoController {
    @Value("${spring2go.swagger.title}")
    private String endpoint;

    @RequestMapping("/getEndpoint")
    public String getEndpoint() {
        return endpoint;
    }

}
