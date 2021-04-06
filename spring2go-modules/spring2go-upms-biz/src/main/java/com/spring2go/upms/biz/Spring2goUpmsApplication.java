package com.spring2go.upms.biz;

import com.spring2go.common.core.feign.EnableSpring2goFeignClients;
import com.spring2go.common.swagger.annotation.EnableCustomSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: 系统管理模块
 * @author: xiaobin
 * @date: 2021/3/29 18:07
 */
@EnableCustomSwagger
@EnableSpring2goFeignClients
@SpringBootApplication
public class Spring2goUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring2goUpmsApplication.class, args);
    }

}
