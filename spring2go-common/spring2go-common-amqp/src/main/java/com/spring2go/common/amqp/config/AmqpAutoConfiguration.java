package com.spring2go.common.amqp.config;

import com.spring2go.common.amqp.template.AmqpTemplate;
import com.spring2go.common.amqp.template.RabbitMqTemplate;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列自动配置
 *
 * @author: xiaobin
 * @date: 2021-06-08 15:38
 */
@Configuration
@AutoConfigureAfter(RabbitAutoConfiguration.class)
@EnableConfigurationProperties(AmqpProperties.class)
public class AmqpAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "spring2go.amqp.enabled", havingValue = "true", matchIfMissing = true)
    public AmqpTemplate rabbitmqTemplate(AmqpAdmin amqpAdmin, RabbitTemplate rabbitTemplate, SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory) {
        return new RabbitMqTemplate(amqpAdmin, rabbitTemplate, simpleRabbitListenerContainerFactory);
    }
}
