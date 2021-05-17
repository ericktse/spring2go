package com.spring2go.upms.biz;


import com.spring2go.common.feign.annotation.EnableFeign;
import com.spring2go.common.swagger.annotation.EnableSwagger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

/**
 * @description: 系统管理模块
 * @author: xiaobin
 * @date: 2021/3/29 18:07
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger
@EnableFeign
public class Spring2goUpmsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(Spring2goUpmsApplication.class, args);
        print(application);
    }

    private static void print(ConfigurableApplicationContext application) {
        try {
            Environment env = application.getEnvironment();
            String ip = InetAddress.getLocalHost().getHostAddress();
            String port = env.getProperty("server.port");
            log.info("\n----------------------------------------------------------\n\t" +
                    "(♥◠‿◠)ﾉﾞ  UPMS 启动成功   ლ(´ڡ`ლ)ﾞ  \n\t" +
                    "Swagger文档: \thttp://" + ip + ":" + port + "/doc.html\n" +
                    "----------------------------------------------------------");
        } catch (Exception e) {
            log.error("启动打印异常：", e);
        }
    }
}
