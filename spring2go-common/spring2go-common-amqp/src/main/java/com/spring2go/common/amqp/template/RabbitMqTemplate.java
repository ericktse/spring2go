package com.spring2go.common.amqp.template;

import com.rabbitmq.client.Channel;
import com.spring2go.common.amqp.annotation.AmqpComponent;
import com.spring2go.common.amqp.annotation.AmqpListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基于RabbitMQ的Template
 *
 * @author: xiaobin
 * @date: 2021-06-08 15:29
 */
@Slf4j
@RequiredArgsConstructor
public class RabbitMqTemplate implements AmqpTemplate, InitializingBean, DisposableBean {

    private final AmqpAdmin rabbitAdmin;
    private final RabbitTemplate rabbitTemplate;
    private final SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory;

    @Resource
    private ApplicationContext applicationContext;

    private List<SimpleMessageListenerContainer> simpleMessageListenerContainerGroup;

    @Override
    public void sendMessage(String exchangeName, String queueName, Object params) {
        log.debug("发送消息到mq");
        sendMessage(exchangeName, queueName, params, 0);
    }

    @Override
    public void sendMessage(String exchangeName, String queueName, Object params, Integer expiration) {
        rabbitTemplate.convertAndSend(exchangeName, queueName, params, message -> {
            if (expiration != null && expiration > 0) {
                message.getMessageProperties().setHeader("x-delay", expiration);
            }
            return message;
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Object> beansWithMqComponentMap = applicationContext.getBeansWithAnnotation(AmqpComponent.class);
        Class<? extends Object> clazz = null;
        for (Map.Entry<String, Object> entry : beansWithMqComponentMap.entrySet()) {
            log.info("初始化时队列............");
            //获取到实例对象的class信息
            clazz = entry.getValue().getClass();
            Method[] methods = clazz.getMethods();
            AmqpComponent amqpComponent = clazz.getAnnotation(AmqpComponent.class);
            if (null != amqpComponent) {
                //#1 使用自定义MqListener
                AmqpListener amqpListener = clazz.getAnnotation(AmqpListener.class);
                if (null != amqpListener) {
                    createQueue(amqpListener.queues(), amqpComponent);
                } else {
                    //#2 使用RabbitListener
                    RabbitListener rabbitListener = clazz.getAnnotation(RabbitListener.class);
                    if (null != rabbitListener) {
                        createQueue(rabbitListener.queues(), amqpComponent);
                    }
                }

                for (Method method : methods) {
                    AmqpListener methodAmqpListener = method.getAnnotation(AmqpListener.class);
                    if (null != methodAmqpListener) {
                        Queue[] queues = createQueue(methodAmqpListener.queues(), amqpComponent);
                        createMessageListenerContainer(queues, method, entry.getValue());
                    } else {
                        RabbitListener methodRabbitListener = method.getAnnotation(RabbitListener.class);
                        if (null != methodRabbitListener) {
                            createQueue(methodRabbitListener.queues(), amqpComponent);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        if (null != simpleMessageListenerContainerGroup) {
            for (SimpleMessageListenerContainer container : simpleMessageListenerContainerGroup) {
                container.stop();
                container.destroy();
            }
        }
    }

    /**
     * 初始化队列
     *
     * @param queues
     */
    private Queue[] createQueue(String[] queues, AmqpComponent amqpComponent) {
        Exchange exchange = createExchange(amqpComponent.exchangeType(), amqpComponent.value(), amqpComponent.durable(), amqpComponent.autoDelete());
        //创建交换机
        rabbitAdmin.declareExchange(exchange);

        ArrayList<Queue> list = new ArrayList<>();
        if (null != queues) {
            for (String queueName : queues) {
                Queue queue = new Queue(queueName);
                rabbitAdmin.declareQueue(queue);

                Binding binding = BindingBuilder.bind(queue).to(exchange).with(queueName).noargs();
                rabbitAdmin.declareBinding(binding);
                log.info("队列创建成功:" + queueName);

                list.add(queue);
            }
        }
        Queue[] queueArray = new Queue[list.size()];
        return list.toArray(queueArray);
    }

    /**
     * 创建交换器
     */
    private Exchange createExchange(String exchangeType, String exchangeName, boolean durable, boolean autoDelete) {
        Exchange exchange;
        if (ExchangeTypes.DIRECT.equals(exchangeType)) {
            exchange = new DirectExchange(exchangeName, durable, autoDelete);
        } else if (ExchangeTypes.TOPIC.equals(exchangeType)) {
            exchange = new TopicExchange(exchangeName, durable, autoDelete);
        } else if (ExchangeTypes.FANOUT.equals(exchangeType)) {
            exchange = new FanoutExchange(exchangeName, durable, autoDelete);
        } else if (ExchangeTypes.HEADERS.equals(exchangeType)) {
            exchange = new HeadersExchange(exchangeName, durable, autoDelete);
        } else {
            exchange = new TopicExchange(exchangeName, durable, autoDelete);
        }

        return exchange;
    }


    /**
     * 创建消息监听容器
     */
    private SimpleMessageListenerContainer createMessageListenerContainer(Queue[] queues, Method handle, Object clazz) {
        SimpleMessageListenerContainer container = simpleRabbitListenerContainerFactory.createListenerContainer();
        //设置接收多个队列里面的消息
        container.setQueues(queues);
        container.setExposeListenerChannel(true);
        //设置最大消费者数量
        container.setMaxConcurrentConsumers(10);
        //设置当前消费者的数量
        container.setConcurrentConsumers(1);
        //设置确认模式手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                try {
                    /**通过basic.qos方法设置prefetch_count=1，这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message，
                     换句话说,在接收到该Consumer的ack前,它不会将新的Message分发给它 */
                    channel.basicQos(1);

                    // TODO:这里使用的是默认SimpleMessageConverter，如果需要实现跨平台，需实现自定义Json MessageConverter
                    Object body = rabbitTemplate.getMessageConverter().fromMessage(message);
                    handle.invoke(clazz, body);

                    /**为了保证永远不会丢失消息，RabbitMQ支持消息应答机制。
                     当消费者接收到消息并完成任务后会往RabbitMQ服务器发送一条确认的命令，然后RabbitMQ才会将消息删除。*/
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (Exception e) {
                    /**
                     * deliveryTag:该消息的index
                     * multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
                     * requeue：被拒绝的是否重新入队列
                     */
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                    throw e;
                }
            }
        });
        container.start();
        if (null == simpleMessageListenerContainerGroup) {
            simpleMessageListenerContainerGroup = new ArrayList<>();
        }
        simpleMessageListenerContainerGroup.add(container);
        return container;
    }

}
