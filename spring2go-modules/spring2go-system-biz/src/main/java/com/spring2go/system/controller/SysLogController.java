package com.spring2go.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.common.core.constant.PageConstants;
import com.spring2go.common.core.controller.BaseController;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.excel.ExcelUtils;
import com.spring2go.common.log.annotation.Log;
import com.spring2go.common.mybatis.util.QueryWrapperUtils;
import com.spring2go.common.security.annotation.Inner;
import com.spring2go.system.entity.SysConfig;
import com.spring2go.system.vo.ConfigVo;
import com.spring2go.system.vo.LogVo;
import com.spring2go.system.entity.SysLog;
import com.spring2go.system.service.SysLogService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description: 系统日志
 * @author: xiaobin
 * @date: 2021-04-06 15:09
 */
@Api(tags = "日志管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class SysLogController extends BaseController {

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
    public R getLogPage(LogVo sysLog) {
        return R.ok(sysLogService.getLogByPage(sysLog));
    }

    /**
     * 插入日志
     *
     * @param sysLog 日志实体
     * @return success/false
     */
    @Inner
    @PostMapping
    public R save(@RequestBody SysLog sysLog)  {
        return R.ok(sysLogService.save(sysLog));
    }

    @Log("日志数据导出")
    @PostMapping("/exportExcel")
    public void export(HttpServletRequest request, HttpServletResponse response, LogVo sysLog) {
        QueryWrapper<SysLog> wrapper = QueryWrapperUtils.initQueryWrapper(sysLog, request.getParameterMap());
        List<SysLog> list = sysLogService.list(wrapper);
        ExcelUtils<SysLog> util = new ExcelUtils<>(SysLog.class);
        util.exportExcel(response, list);
    }
}
