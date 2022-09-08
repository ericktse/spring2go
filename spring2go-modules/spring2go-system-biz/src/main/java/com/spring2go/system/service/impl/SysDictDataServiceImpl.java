package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.system.entity.SysDictData;
import com.spring2go.system.entity.SysDictType;
import com.spring2go.system.mapper.SysDictDataMapper;
import com.spring2go.system.service.SysDictDataService;
import com.spring2go.system.vo.DictVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author xiaobin
 */
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        List<SysDictData> list = baseMapper.selectList(
                Wrappers.<SysDictData>lambdaQuery().eq(SysDictData::getDictType, dictType));
        return list;
    }

    @Override
    public IPage getPage(Page page, DictVo dictVo) {
        LambdaQueryWrapper<SysDictData> queryWrapper = Wrappers.lambdaQuery();
        if (null != dictVo.getDictType()) {
            queryWrapper.like(SysDictData::getDictType, "%" + dictVo.getDictType() + "%");
        }
        if (null != dictVo.getDictLabel()) {
            queryWrapper.like(SysDictData::getDictLabel, "%" + dictVo.getDictLabel() + "%");
        }
        if (null != dictVo.getStatus()) {
            queryWrapper.eq(SysDictData::getStatus, dictVo.getStatus());
        }

        return this.page(page, queryWrapper);
    }
}
