package com.spring2go.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.system.entity.SysConfig;
import com.spring2go.system.vo.ConfigVo;

/**
 * 配置项
 *
 * @author xiaobin
 */
public interface SysConfigService extends IService<SysConfig> {
    IPage getPage(Page page, ConfigVo configVo);
}
