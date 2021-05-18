package com.spring2go.common.lock.Strategy;

import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.lock.config.RedisConnectionType;
import com.spring2go.common.lock.config.RedissonConstant;
import com.spring2go.common.lock.config.RedissonProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 主从方式Redisson配置
 *  <p>配置方式: 127.0.0.1:6379(主),127.0.0.1:6380(子),127.0.0.1:6381(子)</p>
 * @author: xiaobin
 * @date: 2021-05-18 16:55
 */
@Slf4j
public class MasterslaveRedissonConfigStrategy implements RedissonConfigStrategy {
    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(",");
            String masterNodeAddr = addrTokens[0];
            // 设置主节点ip
            config.useMasterSlaveServers().setMasterAddress(masterNodeAddr);
            if (StringUtils.isNotEmpty(password)) {
                config.useMasterSlaveServers().setPassword(password);
            }
            config.useMasterSlaveServers().setDatabase(database);
            // 设置从节点，移除第一个节点，默认第一个为主节点
            List<String> slaveList = new ArrayList<>();
            for (String addrToken : addrTokens) {
                slaveList.add(RedissonConstant.REDIS_CONNECTION_PREFIX + addrToken);
            }
            slaveList.remove(0);

            config.useMasterSlaveServers().addSlaveAddress((String[]) slaveList.toArray());
            log.info("初始化主从方式Config,redisAddress:" + address);
        } catch (Exception e) {
            log.error("主从Redisson初始化错误", e);
            e.printStackTrace();
        }
        return config;
    }

    @Override
    public RedisConnectionType getType() {
        return RedisConnectionType.MASTERSLAVE;
    }
}
