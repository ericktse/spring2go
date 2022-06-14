package com.spring2go.system.feign.service;

import com.spring2go.common.core.constant.SecurityConstants;
import com.spring2go.common.core.constant.ServiceNameConstants;
import com.spring2go.common.core.domain.R;
import com.spring2go.system.entity.SysLog;
import com.spring2go.system.feign.factory.RemoteLogServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @Description: 系统日志远程服务
 * @author: xiaobin
 * @date: 2021-04-06 14:59
 */
@FeignClient(contextId = "RemoteLogService", name = ServiceNameConstants.UPMS_SERVICE, fallbackFactory = RemoteLogServiceFallbackFactory.class)
public interface RemoteLogService {

    /**
     * 保存日志
     *
     * @param sysLog 日志实体
     * @param from   来源
     * @return success、false
     */
    @PostMapping("/sys/log")
    R<Boolean> saveLog(@RequestBody SysLog sysLog, @RequestHeader(SecurityConstants.FROM) String from);
}
