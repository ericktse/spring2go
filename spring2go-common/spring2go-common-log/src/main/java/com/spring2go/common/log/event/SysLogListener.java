package com.spring2go.common.log.event;

import com.spring2go.upms.api.entity.SysLog;
import com.spring2go.upms.api.feign.service.RemoteLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @Description: 系统日志事件监听器
 * @author: xiaobin
 * @date: 2021-04-02 17:24
 */
@Slf4j
@RequiredArgsConstructor
public class SysLogListener {

    private final RemoteLogService remoteLogService;

    //@Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        SysLog sysLog = (SysLog) event.getSource();
        log.debug("打印日志：" + sysLog.getTitle());
        remoteLogService.saveLog(sysLog);
    }

}
