package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.system.entity.SysDictType;
import com.spring2go.system.mapper.SysDictTypeMapper;
import com.spring2go.system.service.SysDictTypeService;
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
}
