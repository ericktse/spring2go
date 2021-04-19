package com.spring2go.upms.biz.controller;

import com.google.common.net.HttpHeaders;
import com.spring2go.common.core.constant.AuthConstants;
import com.spring2go.common.core.constant.PageConstants;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.ServletUtils;
import com.spring2go.upms.api.dto.SysLogDto;
import com.spring2go.upms.api.entity.SysLog;
import com.spring2go.upms.biz.service.SysLogService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 系统日志
 * @author: xiaobin
 * @date: 2021-04-06 15:09
 */
@Api("日志管理模块")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/log")
public class SysLogController {

    private final SysLogService sysLogService;

    /**
     * 分页查询
     *
     * @param sysLog   系统日志
     * @param pageNo   分页下标
     * @param pageSize 分页大小
     * @return
     */
    @GetMapping("/page")
    public R getLogPage(SysLogDto sysLog,
                        @RequestParam(name = "pageNo", defaultValue = PageConstants.DEFAULT_PAGE_NO) Integer pageNo,
                        @RequestParam(name = "pageSize", defaultValue = PageConstants.DEFAULT_PAGE_SIZE) Integer pageSize) {
        return R.ok(sysLogService.getLogByPage(sysLog, pageNo, pageSize));
    }

    /**
     * 插入日志
     *
     * @param sysLog 日志实体
     * @return success/false
     */
    @PostMapping
    public R save(@RequestBody SysLog sysLog) throws InterruptedException {
        //TODO 测试，等待5秒，后续删除
        Thread.sleep(5000);
        return R.ok(sysLogService.save(sysLog));
    }
}
