package com.spring2go.upms.biz.controller;

import com.spring2go.common.rabbitmq.constant.AmqpConstants;
import com.spring2go.common.rabbitmq.template.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: Nacos示例代码
 * @author: xiaobin
 * @date: 2021-04-13 10:00
 */
@RestController
//@RefreshScope // 动态刷新nacos配置。RefreshScope与RestTemplate存在嵌套引用，暂时注释
public class TestController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    // 新增restTemplate对象注入方法，注意，此处LoadBalanced注解一定要加上，否则无法远程调用
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping("/nacos")
    public String hello() {
        return restTemplate.getForObject("http://spring2go-upms/hello/" + "world", String.class);
    }

    @GetMapping(value = "/hello/{name}")
    public String echo(@PathVariable String name) {
        return "Hello Nacos Discovery " + name;
    }


    @Value("${spring2go.swagger.title}")
    private String endpoint;

    @RequestMapping("/getEndpoint")
    public String getEndpoint() {
        return endpoint;
    }


    @GetMapping(value = "/queue/{name}")
    public String queue(@PathVariable String name) {

        Map mao = new HashMap(2);
        mao.put("name", name);
        mao.put("age", 10);
        amqpTemplate.sendMessage(AmqpConstants.DEFAULT_EXCHANGE, AmqpConstants.DEFAULT_QUEUE, mao);

        return "Hello queue " + name;
    }

    @GetMapping(value = "/queue2/{name}")
    public String queue2(@PathVariable String name) {

        amqpTemplate.sendMessage("direct-text2", "direct-test2", name);

        return "Hello queue " + name;
    }
}
