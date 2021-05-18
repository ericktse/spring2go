package com.spring2go.common.lock.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: Redisson配置
 * @author: xiaobin
 * @date: 2021-05-18 16:43
 */
@Data
@ConfigurationProperties(prefix = "spring2go.redisson")
public class RedissonProperties {
    /**
     * redis主机地址，ip：port，多个用逗号(,)分隔
     */
    private String address;
    /**
     * 连接类型
     */
    private RedisConnectionType type;
    /**
     * 密码
     */
    private String password;
    /**
     * 数据库(默认0)
     */
    private int database;

    /**
     * 是否装配redisson配置
     */
    private Boolean enabled = true;
}
