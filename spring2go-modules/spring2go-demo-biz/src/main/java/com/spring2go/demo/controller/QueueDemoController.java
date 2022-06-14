package com.spring2go.demo.controller;

import com.spring2go.common.amqp.constant.AmqpConstants;
import com.spring2go.common.amqp.template.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author xiaobin
 */
@RestController
public class QueueDemoController {

    @Lazy
    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping(value = "/queue/{name}")
    public String queue(@PathVariable String name) {

        Map mao = new HashMap(2);
        mao.put("name", name);
        mao.put("age", 10);
        amqpTemplate.sendMessage(AmqpConstants.DEFAULT_EXCHANGE, AmqpConstants.DEFAULT_QUEUE, mao);

        return "Hello queue " + name;
    }

    @GetMapping(value = "/queue2/{name}")
    public String queue2(@PathVariable String name) {

        amqpTemplate.sendMessage("direct-text2", "direct-test2", name);

        return "Hello queue " + name;
    }
}
