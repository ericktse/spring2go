package com.spring2go.common.interceptor.config;

import com.spring2go.common.interceptor.definition.InterceptorDefinition;
import com.spring2go.common.interceptor.definition.RouteDefinition;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义配置
 *
 * @author xiaobin
 */
@ConfigurationProperties(InterceptorProperties.PREFIX)
@Data
public class InterceptorProperties {

    /**
     * Properties prefix.
     */
    public static final String PREFIX = "spring2go.interceptor";


    private boolean enabled = true;

    /**
     * List of Routes.
     */
    private List<RouteDefinition> routes = new ArrayList<>();
}
