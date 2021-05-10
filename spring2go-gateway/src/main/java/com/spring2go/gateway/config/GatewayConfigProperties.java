package com.spring2go.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 网关自定义配置
 * @author: xiaobin
 * @date: 2021-05-07 15:08
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("spring2go.gateway")
public class GatewayConfigProperties {
    public List<String> whiteList;
}
