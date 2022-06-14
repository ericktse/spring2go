package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.system.entity.SysUserRole;
import com.spring2go.system.mapper.SysUserRoleMapper;
import com.spring2go.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Description: 系统用户服务
 * @author: xiaobin
 * @date: 2021-03-30 10:24
 */
@Service
@RequiredArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {


}
