package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.system.mapper.SysPostMapper;
import com.spring2go.system.service.SysPostService;
import com.spring2go.system.entity.SysPost;
import org.springframework.stereotype.Service;

/**
 * @Description: 系统岗位
 * @author: xiaobin
 * @date: 2021-04-08 11:01
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {
}
