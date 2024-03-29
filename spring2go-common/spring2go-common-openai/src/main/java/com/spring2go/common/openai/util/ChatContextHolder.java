package com.spring2go.common.openai.util;


import com.spring2go.common.openai.entity.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天上下文
 *
 * @author xiaobin
 */
public class ChatContextHolder {

    private static Map<String, List<Message>> context = new HashMap<>();


    /**
     * 获取对话历史
     *
     * @param key
     * @return
     */
    public static List<Message> get(String key) {
        List<Message> messages = context.get(key);
        if (messages == null) {
            messages = new ArrayList<>();
            context.put(key, messages);
        }

        return messages;
    }


    /**
     * 添加对话
     *
     * @param key
     * @return
     */
    public static void add(String key, String msg) {

        Message message = Message.builder().content(msg).build();
        add(key, message);
    }


    /**
     * 添加对话
     *
     * @param key
     * @return
     */
    public static void add(String key, Message message) {
        List<Message> messages = context.get(key);
        if (messages == null) {
            messages = new ArrayList<>();
            context.put(key, messages);
        }
        messages.add(message);
    }
}
