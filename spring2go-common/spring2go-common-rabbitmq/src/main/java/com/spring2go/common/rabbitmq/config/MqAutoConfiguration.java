package com.spring2go.common.rabbitmq.config;

import com.spring2go.common.rabbitmq.template.MqTemplate;
import com.spring2go.common.rabbitmq.template.RabbitMqTemplate;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
public class MqAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MqTemplate rabbitmqTemplate(AmqpAdmin amqpAdmin, RabbitTemplate rabbitTemplate, SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory) {
        return new RabbitMqTemplate(amqpAdmin, rabbitTemplate, simpleRabbitListenerContainerFactory);
    }
}
