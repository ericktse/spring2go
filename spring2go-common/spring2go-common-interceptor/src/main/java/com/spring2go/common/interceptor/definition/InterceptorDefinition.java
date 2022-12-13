package com.spring2go.common.interceptor.definition;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 拦截器定义
 *
 * @author xiaobin
 */
@Data
public class InterceptorDefinition {

    private String name;

    private Map<String, String> args = new LinkedHashMap<>();

    public InterceptorDefinition(String name) {
        this.name = name;
    }
}
