package com.spring2go.upms.biz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.upms.api.dto.SysLogDTO;
import com.spring2go.upms.api.entity.SysLog;

/**
 * @description: 日志服务类
 * @author: xiaobin
 * @date: 2021/4/2 16:41
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 分页查询日志
     *
     * @param sysLog
     * @param pageNo   分页下标
     * @param pageSize 分页大小
     * @return
     */
    Page getLogByPage(SysLogDTO sysLog, Integer pageNo, Integer pageSize);
}
