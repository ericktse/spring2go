package com.spring2go.common.lock.strategy;

import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.lock.config.RedisConnectionType;
import com.spring2go.common.lock.config.RedissonConstant;
import com.spring2go.common.lock.config.RedissonProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

/**
 * @description: 单例模式
 * @author: xiaobin
 * @date: 2021-05-18 16:49
 */
@Slf4j
@Component
public class StandaloneRedissonConfigStrategy implements RedissonConfigStrategy {
    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String redisAddr = RedissonConstant.REDIS_CONNECTION_PREFIX + address;
            config.useSingleServer().setAddress(redisAddr);
            config.useSingleServer().setDatabase(database);
            if (StringUtils.isNotEmpty(password)) {
                config.useSingleServer().setPassword(password);
            }
            log.info("初始化Redisson单机配置,连接地址:" + address);
        } catch (Exception e) {
            log.error("单机Redisson初始化错误", e);
            e.printStackTrace();
        }
        return config;
    }

    @Override
    public RedisConnectionType getType() {
        return RedisConnectionType.STANDALONE;
    }
}
