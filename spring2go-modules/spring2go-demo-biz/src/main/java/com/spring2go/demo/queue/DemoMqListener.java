package com.spring2go.demo.queue;

import com.spring2go.common.amqp.annotation.AmqpComponent;
import com.spring2go.common.amqp.annotation.AmqpListener;
import com.spring2go.common.amqp.constant.AmqpConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

/**
 * 消息队列Demo
 *
 * @author: xiaobin
 * @date: 2021-06-08 16:55
 */
@AmqpComponent(AmqpConstants.DEFAULT_EXCHANGE)
public class DemoMqListener {

    @RabbitListener(queues = AmqpConstants.DEFAULT_QUEUE)
    public void processMessage(@Payload Map body) {
        System.out.println("body：" + body);
    }


    @AmqpListener(queues = "direct-test2")
    public void processMessage(String body) {
        System.out.println("body：" + body);
    }
}
