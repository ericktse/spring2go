package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.system.entity.SysConfig;
import com.spring2go.system.entity.SysDictType;
import com.spring2go.system.mapper.SysDictTypeMapper;
import com.spring2go.system.service.SysDictTypeService;
import com.spring2go.system.vo.DictVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author xiaobin
 */
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {
    @Override
    public IPage getPage(Page page, DictVo dictVo) {
        LambdaQueryWrapper<SysDictType> queryWrapper = Wrappers.lambdaQuery();
        if (null != dictVo.getDictName()) {
            queryWrapper.like(SysDictType::getDictName, "%" + dictVo.getDictName() + "%");
        }
        if (null != dictVo.getDictType()) {
            queryWrapper.like(SysDictType::getDictType, "%" + dictVo.getDictType() + "%");
        }
        if (null != dictVo.getStatus()) {
            queryWrapper.eq(SysDictType::getStatus, dictVo.getStatus());
        }
        if (null != dictVo.getBeginTime()) {
            queryWrapper.ge(SysDictType::getCreateTime, dictVo.getBeginTime());
        }
        if (null != dictVo.getEndTime()) {
            queryWrapper.le(SysDictType::getCreateTime, dictVo.getEndTime());
        }

        return this.page(page, queryWrapper);
    }
}
