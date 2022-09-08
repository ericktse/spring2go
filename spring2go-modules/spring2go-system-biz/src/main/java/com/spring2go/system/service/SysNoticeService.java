package com.spring2go.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.system.entity.SysNotice;

/**
 * 系统通知
 *
 * @author xiaobin
 */
public interface SysNoticeService extends IService<SysNotice> {
    IPage getPage(Page page, SysNotice notice);
}
