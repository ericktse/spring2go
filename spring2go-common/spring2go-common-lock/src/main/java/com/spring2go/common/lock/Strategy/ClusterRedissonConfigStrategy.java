package com.spring2go.common.lock.Strategy;

import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.lock.config.RedisConnectionType;
import com.spring2go.common.lock.config.RedissonConstant;
import com.spring2go.common.lock.config.RedissonProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.config.Config;

/**
 * @description: 集群方式Redisson配置
 * cluster方式至少6个节点(3主3从)
 * 配置方式:127.0.0.1:6379,127.0.0.1:6380,127.0.0.1:6381,127.0.0.1:6382,127.0.0.1:6383,127.0.0.1:6384
 * @author: xiaobin
 * @date: 2021-05-18 16:54
 */
@Slf4j
public class ClusterRedissonConfigStrategy implements RedissonConfigStrategy {
    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            String[] addrTokens = address.split(",");
            // 设置集群(cluster)节点的服务IP和端口
            for (int i = 0; i < addrTokens.length; i++) {
                config.useClusterServers().addNodeAddress(RedissonConstant.REDIS_CONNECTION_PREFIX + addrTokens[i]);
                if (StringUtils.isNotEmpty(password)) {
                    config.useClusterServers().setPassword(password);
                }
            }
            log.info("初始化集群方式Config,连接地址:" + address);
        } catch (Exception e) {
            log.error("集群Redisson初始化错误", e);
            e.printStackTrace();
        }
        return config;
    }

    @Override
    public RedisConnectionType getType() {
        return RedisConnectionType.CLUSTER;
    }
}
