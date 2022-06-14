package com.spring2go.upms.biz.queue;

import com.spring2go.common.rabbitmq.annotation.AmqpComponent;
import com.spring2go.common.rabbitmq.annotation.AmqpListener;

/**
 * 消息队列Demo2
 *
 * @author: xiaobin
 * @date: 2021-06-08 16:55
 */
@AmqpComponent("direct-text2")
public class Demo2MqListener {

    @AmqpListener(queues = "direct-test2")
    public void processMessage(String body) {
        System.out.println("body：" + body);
    }
}
