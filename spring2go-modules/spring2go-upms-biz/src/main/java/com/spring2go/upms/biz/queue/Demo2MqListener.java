package com.spring2go.upms.biz.queue;

import com.spring2go.common.rabbitmq.annotation.MqComponent;
import com.spring2go.common.rabbitmq.annotation.MqListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

/**
 * 消息队列Demo2
 *
 * @author: xiaobin
 * @date: 2021-06-08 16:55
 */
@MqComponent("direct-text2")
public class Demo2MqListener {

    @MqListener(queues = "direct-test2")
    public void processMessage(String body) {
        System.out.println("body：" + body);
    }
}
