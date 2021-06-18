package com.spring2go.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.upms.api.entity.SysMenu;
import com.spring2go.upms.api.entity.SysRole;
import com.spring2go.upms.api.entity.SysRoleMenu;
import com.spring2go.upms.biz.mapper.SysMenuMapper;
import com.spring2go.upms.biz.mapper.SysRoleMapper;
import com.spring2go.upms.biz.mapper.SysRoleMenuMapper;
import com.spring2go.upms.biz.service.SysRoleMenuService;
import com.spring2go.upms.biz.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    /**
     * @param role
     * @param roleId 角色
     * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveRoleMenus(Integer roleId, String menuIds) {

        this.remove(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getRoleId, roleId));

        if (StringUtils.isEmpty(menuIds)) {
            return Boolean.TRUE;
        }
        List<SysRoleMenu> roleMenuList = Arrays.stream(menuIds.split(",")).map(menuId -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(Integer.valueOf(menuId));
            return roleMenu;
        }).collect(Collectors.toList());

        return this.saveBatch(roleMenuList);
    }
}
