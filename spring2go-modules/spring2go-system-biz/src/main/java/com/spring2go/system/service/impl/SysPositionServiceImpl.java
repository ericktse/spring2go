package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.system.entity.SysRole;
import com.spring2go.system.mapper.SysPositionMapper;
import com.spring2go.system.service.SysPositionService;
import com.spring2go.system.entity.SysPosition;
import org.springframework.stereotype.Service;

/**
 * @Description: 系统岗位
 * @author: xiaobin
 * @date: 2021-04-08 11:01
 */
@Service
public class SysPositionServiceImpl extends ServiceImpl<SysPositionMapper, SysPosition> implements SysPositionService {
    @Override
    public IPage getPage(Page page, SysPosition post) {

        LambdaQueryWrapper<SysPosition> queryWrapper = Wrappers.lambdaQuery();
        if (null != post.getPostCode()) {
            queryWrapper.like(SysPosition::getPostCode, "%" + post.getPostCode() + "%");
        }
        if (null != post.getPostName()) {
            queryWrapper.like(SysPosition::getPostName, "%" + post.getPostName() + "%");
        }
        if (null != post.getStatus()) {
            queryWrapper.eq(SysPosition::getStatus, post.getStatus());
        }

        return this.page(page, queryWrapper);
    }
}
