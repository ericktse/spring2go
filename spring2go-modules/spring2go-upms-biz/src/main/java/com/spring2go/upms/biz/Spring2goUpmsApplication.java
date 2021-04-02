package com.spring2go.upms.biz;

import com.spring2go.common.swagger.annotation.EnableCustomSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 系统管理模块
 * @author: xiaobin
 * @date: 2021/3/29 18:07
 */
@EnableCustomSwagger
@SpringBootApplication
public class Spring2goUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring2goUpmsApplication.class, args);
    }

}
