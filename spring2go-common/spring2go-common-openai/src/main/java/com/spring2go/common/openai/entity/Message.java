package com.spring2go.common.openai.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * TODO
 *
 * @author xiaobin
 */
@Data
@AllArgsConstructor
@Builder
public class Message {
    private String role;

    private String content;

    public static Message of(String content) {

        return new Message(Role.USER.getValue(), content);
    }

    public static Message ofSystem(String content) {

        return new Message(Role.SYSTEM.getValue(), content);
    }
}
