package com.spring2go.common.openai.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 选择
 *
 * @author xiaobin
 */
@Data
public class Choice {
    private long index;
    /**
     * 请求参数stream为false返回是message
     */
    private Message message;
    /**
     * 请求参数stream为true返回是delta
     */
    private Message delta;

    @JsonProperty("finish_reason")
    private String finishReason;
}
