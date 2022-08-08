package com.spring2go.common.rule.engine.config;

import com.spring2go.common.rule.engine.reader.AbstractRuleReader;
import com.spring2go.common.rule.engine.reader.XmlRuleReader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 规则引擎配置
 *
 * @author xiaobin
 */
@Configuration
@EnableConfigurationProperties(RuleEngineProperties.class)
@ConditionalOnProperty(value = "spring2go.rule.engine.enabled", havingValue = "true", matchIfMissing = true)
public class RuleEngineAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "spring2go.rule.engine.readerType", havingValue = "XML")
    public AbstractRuleReader rabbitmqTemplate(RuleEngineProperties properties) {
        return new XmlRuleReader(properties);
    }
}
