package com.spring2go.upms.api.feign.service;

import com.spring2go.common.core.constant.ServiceNameConstants;
import com.spring2go.common.core.domain.R;
import com.spring2go.upms.api.entity.SysLog;
import com.spring2go.upms.api.feign.factory.RemoteLogServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description: 系统日志远程服务
 * @author: xiaobin
 * @date: 2021-04-06 14:59
 */
// TODO:由于Spring cloud 2020版本以后，
//  移除了除eureka的所有netflix组件，所以Hystrix，Ribbon,Zuul都已经砍掉
//  后续再研究fallbackFactory实现的Hystrix替代方案：Resilience4j和sentinel（推荐）
@FeignClient(name = ServiceNameConstants.UPMS_SERVICE, fallbackFactory = RemoteLogServiceFallbackFactory.class)
public interface RemoteLogService {

    /**
     * 保存日志
     *
     * @param sysLog 日志实体
     * @return success、false
     */
    @PostMapping("/sys/log")
    R<Boolean> saveLog(@RequestBody SysLog sysLog);
}
