package com.spring2go.common.dictionary.config;

import com.spring2go.common.dictionary.aspect.DictAspect;
import com.spring2go.common.feign.config.FeignAutoConfiguration;
import com.spring2go.common.redis.util.RedisUtils;
import com.spring2go.system.feign.service.RemoteDictService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 数据字典 自动配置
 *
 * @author xiaobin
 */
@Configuration
@AutoConfigureAfter(FeignAutoConfiguration.class)
public class DictionaryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public DictAspect dictAspect(RedisUtils redisUtils, RemoteDictService remoteDictService) {
        return new DictAspect(redisUtils, remoteDictService);
    }
}
