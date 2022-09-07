package com.spring2go.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.system.entity.SysPosition;

/**
 * @description: 系统岗位
 * @author: xiaobin
 * @date: 2021/4/8 11:00
 */
public interface SysPositionService extends IService<SysPosition> {

    IPage getPage(Page page, SysPosition post);
}
