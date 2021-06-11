package com.spring2go.upms.biz.queue;

import com.spring2go.common.rabbitmq.annotation.MqComponent;
import com.spring2go.common.rabbitmq.constant.QueueConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

/**
 * 消息队列Demo
 *
 * @author: xiaobin
 * @date: 2021-06-08 16:55
 */
@MqComponent(QueueConstants.DEFAULT_EXCHANGE)
public class DemoMqListener {

    @RabbitListener(queues = QueueConstants.DEFAULT_QUEUE)
    public void processMessage(@Payload Map body) {
        System.out.println("body：" + body);
    }
}
