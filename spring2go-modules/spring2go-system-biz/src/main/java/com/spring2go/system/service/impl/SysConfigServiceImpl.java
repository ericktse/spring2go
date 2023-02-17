package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.system.entity.SysConfig;
import com.spring2go.system.mapper.SysConfigMapper;
import com.spring2go.system.service.SysConfigService;
import com.spring2go.system.vo.ConfigVo;
import org.springframework.stereotype.Service;

/**
 * 配置项
 *
 * @author xiaobin
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {
    @Override
    public IPage getPage(Page page, ConfigVo configVo) {
        LambdaQueryWrapper<SysConfig> queryWrapper = Wrappers.lambdaQuery();
        if (null != configVo.getConfigName()) {
            queryWrapper.like(SysConfig::getConfigName, "%" + configVo.getConfigName() + "%");
        }
        if (null != configVo.getConfigKey()) {
            queryWrapper.like(SysConfig::getConfigKey, "%" + configVo.getConfigKey() + "%");
        }
        if (null != configVo.getConfigType()) {
            queryWrapper.eq(SysConfig::getConfigType, configVo.getConfigType());
        }
        if (null != configVo.getBeginTime()) {
            queryWrapper.ge(SysConfig::getCreateTime, configVo.getBeginTime());
        }
        if (null != configVo.getEndTime()) {
            queryWrapper.le(SysConfig::getCreateTime, configVo.getEndTime());
        }

        return this.page(page, queryWrapper);
    }

    @Override
    public String selectConfigByKey(String configKey) {
        LambdaQueryWrapper<SysConfig> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysConfig::getConfigKey, configKey);
        SysConfig retConfig = this.getOne(queryWrapper);
        if (StringUtils.isNotNull(retConfig)) {
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }
}
