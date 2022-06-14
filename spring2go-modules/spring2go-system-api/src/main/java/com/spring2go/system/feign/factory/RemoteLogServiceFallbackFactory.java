package com.spring2go.system.feign.factory;

import com.spring2go.system.feign.fallback.RemoteLogServiceFallbackImpl;
import com.spring2go.system.feign.service.RemoteLogService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: 日志服务Feign调用熔断处理
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
