package com.spring2go.common.rabbitmq.template;

/**
 * 消息队列Template
 *
 * @author: xiaobin
 * @date: 2021-06-08 15:27
 */
public interface MqTemplate {

    /**
     * 发送消息
     * @param exchangeName: 路由器名称
     * @param queueName: 队列名称
     * @param params: 参数
     * @return void
    */
    void sendMessage(String exchangeName, String queueName, Object params);

    /**
     * TODO
     * @param exchangeName: 路由器名称
     * @param queueName: 队列名称
     * @param params: 参数
     * @param expiration: 延迟时间（秒）
     * @return void
    */ 
    void sendMessage(String exchangeName, String queueName, Object params, Integer expiration);
}
