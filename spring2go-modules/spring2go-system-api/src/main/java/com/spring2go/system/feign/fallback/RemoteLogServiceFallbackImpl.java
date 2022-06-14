package com.spring2go.system.feign.fallback;

import com.spring2go.common.core.domain.R;
import com.spring2go.system.entity.SysLog;
import com.spring2go.system.feign.service.RemoteLogService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 日志服务熔断默认实现
 * @author: xiaobin
 * @date: 2021-04-06 15:01
 */
@Slf4j
public class RemoteLogServiceFallbackImpl implements RemoteLogService {

    @Setter
    private Throwable cause;

    @Override
    public R<Boolean> saveLog(SysLog sysLog, String from) {
        log.error("feign 插入日志失败", cause);
        return R.failed("熔断");
    }
}
