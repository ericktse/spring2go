package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
}
