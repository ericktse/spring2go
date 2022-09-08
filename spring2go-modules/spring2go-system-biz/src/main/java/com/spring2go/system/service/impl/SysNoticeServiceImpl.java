package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.system.entity.SysConfig;
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
    @Override
    public IPage getPage(Page page, SysNotice notice) {
        LambdaQueryWrapper<SysNotice> queryWrapper = Wrappers.lambdaQuery();
        if (null != notice.getNoticeTitle()) {
            queryWrapper.like(SysNotice::getNoticeTitle, "%" + notice.getNoticeTitle() + "%");
        }
        if (null != notice.getCreateBy()) {
            queryWrapper.like(SysNotice::getCreateBy, "%" + notice.getCreateBy() + "%");
        }
        if (null != notice.getNoticeType()) {
            queryWrapper.eq(SysNotice::getNoticeType, notice.getNoticeType());
        }

        return this.page(page, queryWrapper);
    }
}
