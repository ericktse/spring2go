package com.spring2go.common.log.aspect;

import com.spring2go.common.core.util.IpUtils;
import com.spring2go.common.core.util.ServletUtils;
import com.spring2go.common.core.util.SpringContextHolder;
import com.spring2go.common.log.annotation.Log;
import com.spring2go.common.log.event.SysLogEvent;
import com.spring2go.upms.api.entity.SysLog;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Description: 系统日志拦截器
 * @author: xiaobin
 * @date: 2021-04-02 16:55
 */
@Aspect
@Slf4j
public class SysLogAspect {

    @Around("@annotation(sysLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, Log sysLog) {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

        SysLog logVo = getSysLog();
        logVo.setTitle(sysLog.value());

        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();
        Object obj;

        try {
            obj = point.proceed();
        } catch (Exception e) {
            logVo.setType("1");
            logVo.setException(e.getMessage());
            throw e;
        } finally {
            Long endTime = System.currentTimeMillis();
            logVo.setTime(endTime - startTime);
            SpringContextHolder.publishEvent(new SysLogEvent(logVo));
        }

        return obj;
    }


    private SysLog getSysLog() {
        SysLog sysLog = new SysLog();
        sysLog.setCreateBy(Objects.requireNonNull(""));
        sysLog.setType("0");
        HttpServletRequest request = ServletUtils.getRequest();
        sysLog.setRemoteAddr(IpUtils.getIpAddr(request));
        sysLog.setRequestUri(request.getRequestURI());
        sysLog.setMethod(request.getMethod());
        sysLog.setUserAgent(request.getHeader("user-agent"));
        sysLog.setParams("");
        sysLog.setServiceId("");
        return sysLog;
    }

}
