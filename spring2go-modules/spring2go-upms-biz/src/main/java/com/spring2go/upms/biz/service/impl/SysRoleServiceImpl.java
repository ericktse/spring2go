package com.spring2go.upms.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.upms.api.entity.SysRole;
import com.spring2go.upms.biz.mapper.SysRoleMapper;
import com.spring2go.upms.biz.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * @Description: 系统角色
 * @author: xiaobin
 * @date: 2021-04-08 11:01
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
