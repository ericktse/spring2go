package com.spring2go.gateway;

import com.spring2go.common.swagger.annotation.EnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: 应用网关
 * @author: xiaobin
 * @date: 2021/5/7 14:25
 */
@EnableSwagger
@SpringBootApplication
public class Spring2goGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring2goGatewayApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  网关启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }

}
