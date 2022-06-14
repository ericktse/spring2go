package com.spring2go.system.feign.factory;

import com.spring2go.system.feign.fallback.RemoteUserServiceFallbackImpl;
import com.spring2go.system.feign.service.RemoteUserService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: 日志服务Feign调用熔断处理
 * @author: xiaobin
 * @date: 2021-04-06 15:00
 */
@Component
public class RemoteUserServiceFallbackFactory implements FallbackFactory<RemoteUserService> {

    @Override
    public RemoteUserService create(Throwable cause) {
        return new RemoteUserServiceFallbackImpl(cause);
    }
}
