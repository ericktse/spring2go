package com.spring2go.common.log.aspect;

import com.spring2go.common.core.util.*;
import com.spring2go.common.log.annotation.Log;
import com.spring2go.common.log.event.SysLogEvent;
import com.spring2go.common.log.util.LogType;
import com.spring2go.upms.api.entity.SysLog;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
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

        SysLog logVo = getSysLog(sysLog);

        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();
        Object obj = null;

        try {
            obj = point.proceed();
        } catch (Exception e) {
            logVo.setType(LogType.ERROR.getType());
            logVo.setException(e.getMessage());
            throw e;
        } finally {
            Long endTime = System.currentTimeMillis();
            logVo.setTime(endTime - startTime);
            if (obj != null) {
                logVo.setResult(StringUtils.substring(obj.toString(), 0, 2000));
            }
            SpringContextHolder.publishEvent(new SysLogEvent(logVo));
        }

        return obj;
    }

    private SysLog getSysLog(Log log) {
        SysLog sysLog = new SysLog();
        sysLog.setTitle(log.value());
        sysLog.setCreateBy(Objects.requireNonNull("admin"));
        sysLog.setCreateTime(DateUtils.now());
        sysLog.setType(LogType.NORMAL.getType());
        HttpServletRequest request = ServletUtils.getRequest();
        sysLog.setRemoteAddr(IpUtils.getIpAddr(request));
        sysLog.setRequestUri(request.getRequestURI());
        sysLog.setMethod(request.getMethod());
        sysLog.setUserAgent(request.getHeader("user-agent"));
        String params = "";
        Map<String, String[]> map = request.getParameterMap();
        for (String key : request.getParameterMap().keySet()) {
            String[] value = map.get(key);
            params = StringUtils.isNotEmpty(params) ? params + "," + key + "=" + Arrays.toString(value) : key + "=" + Arrays.toString(value);
        }
        sysLog.setParams(StringUtils.substring(params, 0, 2000));
        return sysLog;
    }

}
