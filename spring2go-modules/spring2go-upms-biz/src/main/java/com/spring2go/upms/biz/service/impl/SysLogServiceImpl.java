package com.spring2go.upms.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.upms.api.entity.SysLog;
import com.spring2go.upms.biz.mapper.SysLogMapper;
import com.spring2go.upms.biz.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * @Description: 日志服务实现类
 * @author: xiaobin
 * @date: 2021-04-02 16:42
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
}
