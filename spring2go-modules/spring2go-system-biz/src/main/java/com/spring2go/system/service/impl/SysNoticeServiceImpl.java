package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.system.entity.SysNotice;
import com.spring2go.system.mapper.SysNoticeMapper;
import com.spring2go.system.service.SysNoticeService;
import org.springframework.stereotype.Service;

/**
 * 配置项
 *
 * @author xiaobin
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {
}
