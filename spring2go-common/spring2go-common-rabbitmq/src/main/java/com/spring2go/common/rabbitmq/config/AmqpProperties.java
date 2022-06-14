package com.spring2go.common.rabbitmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义队列配置
 *
 * @author xiaobin
 */
@ConfigurationProperties(prefix = "spring2go.amqp")
public class AmqpProperties {
    /**
     * 是否启用
     */
    private Boolean enabled = true;
}
