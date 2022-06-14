package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.system.mapper.SysMenuMapper;
import com.spring2go.system.entity.SysMenu;
import com.spring2go.system.entity.SysRole;
import com.spring2go.system.mapper.SysRoleMapper;
import com.spring2go.system.service.SysRoleService;
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
            all.addAll(sysMenuMapper.listMenusByRoleId(role.getRoleId().toString()));
        });

        Set<String> perms = all.stream().map(SysMenu::getPerms).collect(Collectors.toSet());
        return perms;
    }
}
