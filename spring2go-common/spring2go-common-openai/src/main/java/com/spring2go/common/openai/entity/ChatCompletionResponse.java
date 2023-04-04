package com.spring2go.common.openai.entity;

import lombok.Data;

import java.util.List;

/**
 * Chat返回报文
 *
 * @author xiaobin
 */
@Data
public class ChatCompletionResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
}
