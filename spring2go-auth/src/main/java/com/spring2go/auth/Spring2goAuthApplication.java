package com.spring2go.auth;

import com.spring2go.common.feign.annotation.EnableFeign;
import com.spring2go.common.swagger.annotation.EnableSwagger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

/**
 * @author xiaobin
 * @description 认证授权中心
 */
@EnableSwagger
@EnableFeign
@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class Spring2goAuthApplication {

    public static void main(String[] args) {
        print(SpringApplication.run(Spring2goAuthApplication.class, args));
    }

    private static void print(ConfigurableApplicationContext application) {
        try {
            Environment env = application.getEnvironment();
            String ip = InetAddress.getLocalHost().getHostAddress();
            String port = env.getProperty("server.port");
            log.info("\n----------------------------------------------------------\n\t" +
                    "(♥◠‿◠)ﾉﾞ  认证授权中心启动成功   ლ(´ڡ`ლ)ﾞ  \n\t" +
                    "Swagger文档: \thttp://" + ip + ":" + port + "/doc.html\n\t" +
                    "Swagger文档: \thttp://localhost:"+ port + "/doc.html\n" +
                    "----------------------------------------------------------");
        } catch (Exception e) {
            log.error("启动打印异常：", e);
        }
    }

}
