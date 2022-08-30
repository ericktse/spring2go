package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.security.util.SecurityUtils;
import com.spring2go.system.entity.SysDepartment;
import com.spring2go.system.mapper.SysMenuMapper;
import com.spring2go.system.entity.SysMenu;
import com.spring2go.system.entity.SysRole;
import com.spring2go.system.mapper.SysRoleMapper;
import com.spring2go.system.service.SysRoleService;
import com.spring2go.system.vo.RoleVo;
import com.spring2go.system.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: 系统角色
 * @author: xiaobin
 * @date: 2021-04-08 11:01
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysMenuMapper sysMenuMapper;

    @Override
    public List<SysRole> getRoleByUserName(String username) {
        return sysRoleMapper.getRoleByUserName(username);
    }

    @Override
    public Set<String> getPermsByUserName(String username) {

        List<SysRole> roles = getRoleByUserName(username);

        List<SysMenu> all = new ArrayList<>();
        roles.stream().forEach(role -> {
            if (SecurityUtils.isAdmin(role.getRoleName())) {
                LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery();
                queryWrapper.eq(SysMenu::getDelFlag, "0");

                all.addAll(sysMenuMapper.selectList(queryWrapper));
            } else {
                all.addAll(sysMenuMapper.listMenusByRoleName(role.getRoleName()));
            }
        });

        Set<String> perms = all.stream().map(SysMenu::getPerms).collect(Collectors.toSet());
        return perms;
    }

    @Override
    public IPage getPage(Page page, RoleVo roleVo) {
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery();
        if (null != roleVo.getRoleName()) {
            queryWrapper.like(SysRole::getRoleName, "%" + roleVo.getRoleName() + "%");
        }
        if (null != roleVo.getRoleKey()) {
            queryWrapper.like(SysRole::getRoleKey, "%" + roleVo.getRoleKey() + "%");
        }
        if (null != roleVo.getStatus()) {
            queryWrapper.eq(SysRole::getStatus, roleVo.getStatus());
        }
        if (null != roleVo.getBeginTime()) {
            queryWrapper.ge(SysRole::getCreateTime, roleVo.getBeginTime());
        }
        if (null != roleVo.getEndTime()) {
            queryWrapper.le(SysRole::getCreateTime, roleVo.getEndTime());
        }

        return this.page(page, queryWrapper);
    }
}
