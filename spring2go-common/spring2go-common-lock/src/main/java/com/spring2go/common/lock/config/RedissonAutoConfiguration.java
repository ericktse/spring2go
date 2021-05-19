package com.spring2go.common.lock.config;

import com.spring2go.common.lock.annotation.DistributedLock;
import com.spring2go.common.lock.strategy.RedissonConfigFactory;
import com.spring2go.common.lock.aspect.DistributedLockAspect;
import com.spring2go.common.lock.util.DistributedLockClient;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Redisson自动配置
 * @author: xiaobin
 * @date: 2021-05-18 16:45
 */
@Configuration
@ConditionalOnClass(RedissonProperties.class)
@EnableConfigurationProperties(RedissonProperties.class)
@Slf4j
@ComponentScan(basePackages = {"com.spring2go.common.lock.strategy"})
public class RedissonAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient(RedissonProperties redissonProperties) {
        RedissonConfigFactory redissonConfigFactory = new RedissonConfigFactory();
        log.info("RedissonManager初始化完成,当前连接方式:" + redissonProperties.getType() + ",连接地址:" + redissonProperties.getAddress());
        return redissonConfigFactory.createRedissonClient(redissonProperties);
    }

    @Bean
    public DistributedLockClient distributeLockClient(RedissonClient redissonClient) {
        return new DistributedLockClient(redissonClient);
    }

    @Bean
    public DistributedLockAspect distributedLockAspect(RedissonClient redissonClient) {
        return new DistributedLockAspect(redissonClient);
    }
}
