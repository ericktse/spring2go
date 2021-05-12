package com.spring2go.auth;

import com.spring2go.common.feign.annotation.EnableFeign;
import com.spring2go.common.swagger.annotation.EnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description 认证授权中心
 * @author xiaobin
 */
@EnableSwagger
@EnableFeign
@EnableDiscoveryClient
@SpringBootApplication
public class Spring2goAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring2goAuthApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  认证授权中心启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }

}
