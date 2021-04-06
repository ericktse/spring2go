package com.spring2go.upms.api.feign.factory;

import com.spring2go.upms.api.feign.fallback.RemoteLogServiceFallbackImpl;
import com.spring2go.upms.api.feign.service.RemoteLogService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: Feign调用熔断处理
 * @author: xiaobin
 * @date: 2021-04-06 15:00
 */
@Component
public class RemoteLogServiceFallbackFactory implements FallbackFactory<RemoteLogService> {
    @Override
    public RemoteLogService create(Throwable cause) {
        RemoteLogServiceFallbackImpl remoteLogServiceFallback = new RemoteLogServiceFallbackImpl();
        remoteLogServiceFallback.setCause(cause);
        return remoteLogServiceFallback;
    }
}
