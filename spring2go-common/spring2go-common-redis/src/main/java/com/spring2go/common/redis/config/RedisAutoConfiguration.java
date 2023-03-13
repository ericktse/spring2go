package com.spring2go.common.redis.config;

import com.spring2go.common.redis.util.RedisUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @description: redis自动配置
 * @author: xiaobin
 * @date: 2021-05-12 16:34
 */
@Configuration
public class RedisAutoConfiguration {

    @Bean
    public RedisUtils redisUtils(RedisTemplate redisTemplate) {
        return new RedisUtils(redisTemplate);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        //设置默认的Serialize，包含 keySerializer & valueSerializer
        template.setDefaultSerializer(fastJsonRedisSerializer);

        return template;
    }
}
