package com.spring2go.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: 服务监控
 * @author: xiaobin
 * @date: 2021/5/20 11:36
 */
@EnableAdminServer
@SpringBootApplication
public class Spring2goMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring2goMonitorApplication.class, args);
    }

}
