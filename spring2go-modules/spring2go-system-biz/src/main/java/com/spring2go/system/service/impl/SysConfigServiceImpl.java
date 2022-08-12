package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.system.entity.SysConfig;
import com.spring2go.system.mapper.SysConfigMapper;
import com.spring2go.system.service.SysConfigService;
import org.springframework.stereotype.Service;

/**
 * 配置项
 *
 * @author xiaobin
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {
}
