package com.spring2go.common.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 自定义Shiro配置
 * @author: xiaobin
 * @date: 2021-05-17 17:41
 */
@Data
@ConfigurationProperties("spring2go.shiro")
public class ShiroProperties {

    private final static String PATH_SPLIT_N = "\n";
    private final static String PATH_SPLIT_E = ":";

    public Map<String, String> getPathDefinitions() {
        Map<String, String> map = new HashMap<>();
        for (String path : pathDefinitions.split(PATH_SPLIT_N)) {
            String[] val = path.split(PATH_SPLIT_E);
            map.put(val[0], val[1]);
        }
        return map;
    }

    public void setPathDefinitions(String pathDefinitions) {
        this.pathDefinitions = pathDefinitions;
    }

    /**
     * 匿名访问路径
     */
    private String pathDefinitions;
}
