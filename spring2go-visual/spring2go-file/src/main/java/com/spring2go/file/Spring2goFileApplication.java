package com.spring2go.file;

import com.spring2go.common.swagger.annotation.EnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 文件服务
 *
 * @author: xiaobin
 * @date: 2021/5/21 10:37
 */
@EnableSwagger
@SpringBootApplication
public class Spring2goFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring2goFileApplication.class, args);
    }

}
