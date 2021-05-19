package com.spring2go.common.lock.strategy;

import com.spring2go.common.core.util.SpringContextHolder;
import com.spring2go.common.lock.config.RedisConnectionType;
import com.spring2go.common.lock.config.RedissonProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: Redisson连接方式配置工厂
 * @author: xiaobin
 * @date: 2021-05-18 16:58
 */
public class RedissonConfigFactory {

    private Map<String, RedissonConfigStrategy> redissonConfigStrategys;

    public RedissonConfigFactory() {
        redissonConfigStrategys = new HashMap<>();

        Map<String, RedissonConfigStrategy> strategys = SpringContextHolder.getApplicationContext().getBeansOfType(RedissonConfigStrategy.class);
        strategys.forEach((k, v) -> {
            redissonConfigStrategys.put(v.getType().getCode(), v);
        });
    }

    public RedissonClient createRedissonClient(RedissonProperties redissonProperties) {
        RedisConnectionType connectionType = redissonProperties.getType();
        RedissonConfigStrategy redissonConfigStrategy = redissonConfigStrategys.get(connectionType.getCode());

        Config config = redissonConfigStrategy.createRedissonConfig(redissonProperties);
        RedissonClient redissonClient = Redisson.create(config);

        return redissonClient;
    }
}
