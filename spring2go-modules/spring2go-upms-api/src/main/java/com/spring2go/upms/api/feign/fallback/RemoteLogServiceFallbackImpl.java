package com.spring2go.upms.api.feign.fallback;

import com.spring2go.common.core.domain.R;
import com.spring2go.upms.api.entity.SysLog;
import com.spring2go.upms.api.feign.service.RemoteLogService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description: 熔断默认实现
 * @author: xiaobin
 * @date: 2021-04-06 15:01
 */
@Slf4j
@Component
public class RemoteLogServiceFallbackImpl implements RemoteLogService {

    @Setter
    private Throwable cause;

    @Override
    public R<Boolean> saveLog(SysLog sysLog) {

        log.error("feign 插入日志失败", cause);
        return null;
    }
}
