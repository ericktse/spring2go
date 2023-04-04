package com.spring2go.common.openai.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色
 *
 * @author xiaobin
 */
@Getter
@AllArgsConstructor
public enum Role {

    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant");

    private String value;
}