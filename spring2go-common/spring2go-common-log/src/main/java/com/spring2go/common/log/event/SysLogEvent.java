package com.spring2go.common.log.event;

import com.spring2go.system.entity.SysLog;
import org.springframework.context.ApplicationEvent;

/**
 * @Description: 系统日志事件
 * @author: xiaobin
 * @date: 2021-04-02 17:24
 */
public class SysLogEvent extends ApplicationEvent {

    public SysLogEvent(SysLog source) {
        super(source);
    }

}
