package com.spring2go.upms.biz;

import com.spring2go.common.core.feign.EnableSpring2goFeignClients;
import com.spring2go.common.swagger.annotation.EnableCustomSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description: 系统管理模块
 * @author: xiaobin
 * @date: 2021/3/29 18:07
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCustomSwagger
@EnableSpring2goFeignClients
public class Spring2goUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring2goUpmsApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  UPMS 启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }

}
