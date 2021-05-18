package com.spring2go.common.lock.Strategy;

import com.spring2go.common.lock.config.RedisConnectionType;
import com.spring2go.common.lock.config.RedissonProperties;
import org.redisson.config.Config;

/**
 * @description: Redisson配置构建接口
 * @author: xiaobin
 * @date: 2021-05-18 16:47
 */
public interface RedissonConfigStrategy {

    /**
     * 根据不同的Redis配置策略创建对应的Config
     *
     * @param redissonProperties
     * @return Config
     */
    Config createRedissonConfig(RedissonProperties redissonProperties);

    /**
     * @description 获取redis连接类型
     */
    RedisConnectionType getType();
}
