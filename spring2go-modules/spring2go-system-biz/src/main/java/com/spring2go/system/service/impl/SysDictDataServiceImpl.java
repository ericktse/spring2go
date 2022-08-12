package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.system.entity.SysDictData;
import com.spring2go.system.mapper.SysDictDataMapper;
import com.spring2go.system.service.SysDictDataService;
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
}
