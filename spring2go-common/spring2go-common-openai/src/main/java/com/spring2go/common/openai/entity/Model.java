package com.spring2go.common.openai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 模型
 *
 * @author xiaobin
 */
@Getter
@AllArgsConstructor
public enum Model {
    /**
     * gpt-3.5-turbo
     */
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    /**
     * 临时模型，不建议使用
     */
    GPT_3_5_TURBO_0301("gpt-3.5-turbo-0301"),
    /**
     * GPT4.0
     */
    GPT_4("gpt-4"),
    /**
     * 临时模型，不建议使用
     */
    GPT_4_0314("gpt-4-0314"),
    /**
     * GPT4.0 超长上下文
     */
    GPT_4_32K("gpt-4-32k"),
    /**
     * 临时模型，不建议使用
     */
    GPT_4_32K_0314("gpt-4-32k-0314");
    private String name;
}
