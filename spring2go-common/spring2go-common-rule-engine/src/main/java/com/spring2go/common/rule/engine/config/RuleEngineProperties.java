package com.spring2go.common.rule.engine.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 规则引擎配置参数
 *
 * @author xiaobin
 */
@Data
@ConfigurationProperties("spring2go.rule.engine")
public class RuleEngineProperties {

    private boolean enabled = true;

    /**
     * reader类型
     * XML、DB
     */
    private String readerType = "XML";

    /**
     * XML配置文件地址
     */
    private String xmlFile;

}
