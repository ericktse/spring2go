package com.spring2go.common.openai.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 聊天请求
 *
 * @author xiaobin
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatCompletion {

    /**
     * 模型，必填
     */
    @Builder.Default
    private String model = Model.GPT_3_5_TURBO.getName();

    /**
     * 消息，必填
     */
    private List<Message> messages;

    /**
     * 采样温度，0-2之间
     * 默认值1，不要同时修改temperature和top_p
     */
    @Builder.Default
    private Double temperature = null;

    /**
     * 核采样,一种替代温度采样的方法，0-1之间
     * 默认值1，不要同时修改temperature和top_p
     */
    @Builder.Default
    private Double top_p = null;

    /**
     * 默认值1，每个输入消息生成多少个聊天
     */
    @Builder.Default
    private Integer n = null;

    /**
     * 默认值false，是否流式输出
     */
    @Builder.Default
    private Boolean stream = null;

    /**
     * 默认值null，停用词
     */
    @Builder.Default
    private List<String> stop = null;

    /**
     * 3.5 最大支持4096
     * 4.0 最大32k
     */
    @JsonProperty("max_tokens")
    @Builder.Default
    private Integer maxTokens = null;

    /**
     * 默认值0,-2.0-2.0 之间
     */
    @JsonProperty("presence_penalty")
    @Builder.Default
    private Double presencePenalty = null;

    /**
     * 默认值0,-2.0-2.0之间
     */
    @JsonProperty("frequency_penalty")
    @Builder.Default
    private Double frequencyPenalty = null;

    /**
     * 默认值null
     *
     * @author xiaobin
     */
    @JsonProperty("logit_bias")
    @Builder.Default
    private Map logitBias = null;

    /**
     * 用户唯一值，确保接口不被重复调用
     */
    @Builder.Default
    private String user = null;
}

