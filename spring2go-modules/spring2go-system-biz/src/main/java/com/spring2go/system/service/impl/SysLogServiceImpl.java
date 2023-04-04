package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.system.entity.SysConfig;
import com.spring2go.system.vo.LogVo;
import com.spring2go.system.entity.SysLog;
import com.spring2go.system.mapper.SysLogMapper;
import com.spring2go.system.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * @Description: 日志服务实现类
 * @author: xiaobin
 * @date: 2021-04-02 16:42
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    /**
     * 分页查询日志
     *
     * @param sysLog
     * @return
     */
    @Override
    public Page getLogByPage(LogVo sysLog) {
        LambdaQueryWrapper<SysLog> wrapper = Wrappers.lambdaQuery();

        if (null != sysLog.getTitle()) {
            wrapper.like(SysLog::getTitle, "%" + sysLog.getTitle() + "%");
        }
        if (null != sysLog.getCreateBy()) {
            wrapper.like(SysLog::getCreateBy, "%" + sysLog.getCreateBy() + "%");
        }
        if (StringUtils.isNotEmpty(sysLog.getResponseStatus())) {
            wrapper.eq(SysLog::getResponseStatus, sysLog.getResponseStatus());
        }
        if (sysLog.getBeginTime() != null) {
            wrapper.ge(SysLog::getCreateTime, sysLog.getBeginTime());
        }
        if (sysLog.getEndTime() != null) {
            wrapper.le(SysLog::getCreateTime, sysLog.getEndTime());
        }
        wrapper.orderByDesc(SysLog::getCreateTime);

        Page page = new Page(sysLog.getPageNum(), sysLog.getPageSize());
        return this.page(page, wrapper);
    }
}
