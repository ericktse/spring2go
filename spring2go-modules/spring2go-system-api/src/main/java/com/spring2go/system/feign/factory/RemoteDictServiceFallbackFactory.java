package com.spring2go.system.feign.factory;

import com.spring2go.system.feign.fallback.RemoteDictServiceFallbackImpl;
import com.spring2go.system.feign.fallback.RemoteLogServiceFallbackImpl;
import com.spring2go.system.feign.service.RemoteDictService;
import com.spring2go.system.feign.service.RemoteLogService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: 日志服务Feign调用熔断处理
 * @author: xiaobin
 * @date: 2021-04-06 15:00
 */
@Component
public class RemoteDictServiceFallbackFactory implements FallbackFactory<RemoteDictService> {

    @Override
    public RemoteDictService create(Throwable cause) {
        RemoteDictServiceFallbackImpl fallback = new RemoteDictServiceFallbackImpl(cause);
        return fallback;
    }
}
