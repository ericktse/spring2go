package com.spring2go.gateway;

import com.spring2go.common.swagger.annotation.EnableSwagger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

/**
 * @description: 应用网关
 * @author: xiaobin
 * @date: 2021/5/7 14:25
 */
@EnableSwagger
@SpringBootApplication
@Slf4j
public class Spring2goGatewayApplication {

    public static void main(String[] args) {
        print(SpringApplication.run(Spring2goGatewayApplication.class, args));
    }

    private static void print(ConfigurableApplicationContext application) {
        try {
            Environment env = application.getEnvironment();
            String ip = InetAddress.getLocalHost().getHostAddress();
            String port = env.getProperty("server.port");
            log.info("\n----------------------------------------------------------\n\t" +
                    "(♥◠‿◠)ﾉﾞ  网关启动成功   ლ(´ڡ`ლ)ﾞ  \n\t" +
                    "Swagger文档: \thttp://" + ip + ":" + port + "/doc.html\n" +
                    "----------------------------------------------------------");
        } catch (Exception e) {
            log.error("启动打印异常：", e);
        }
    }
}
