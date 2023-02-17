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

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    String selectConfigByKey(String configKey);
}
