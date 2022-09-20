package com.spring2go.common.interceptor.definition;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 拦截器定义
 *
 * @author xiaobin
 */
@Validated
@Data
public class InterceptorDefinition {
    @NotNull
    private String name;

    private Map<String, String> args = new LinkedHashMap<>();
}
