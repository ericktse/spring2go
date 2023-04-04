package com.spring2go.common.openai.entity;

import lombok.Data;

import java.util.List;

/**
 * 基础返回参数
 *
 * @author xiaobin
 */
@Data
public class BaseResponse<T> {
    private String object;
    private List<T> data;
    private Error error;


    @Data
    public class Error {
        private String message;
        private String type;
        private String param;
        private String code;
    }
}