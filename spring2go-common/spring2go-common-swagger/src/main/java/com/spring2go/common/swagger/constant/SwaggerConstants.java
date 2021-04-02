package com.spring2go.common.swagger.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: swagger 常量
 * @author: xiaobin
 * @date: 2021-03-31 11:38
 */
public interface SwaggerConstants {

    /**
     * 默认校验KEY
     */
    String ACCESS_TOKEN_KEY = "Authorization";

    /**
     * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
     */
    List<String> DEFAULT_EXCLUDE_PATH = Arrays.asList("/error", "/actuator/**");

    /**
     * 默认根路径
     */
    String DEFAULT_BASE_PATH = "/**";

    /**
     * 默认包
     */
    String BASE_PACKAGE = "com.spring2go";
}
