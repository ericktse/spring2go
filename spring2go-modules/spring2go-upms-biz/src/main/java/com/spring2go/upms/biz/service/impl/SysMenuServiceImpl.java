package com.spring2go.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.common.core.util.TreeUtils;
import com.spring2go.upms.api.dto.DeptTree;
import com.spring2go.upms.api.dto.MenuTree;
import com.spring2go.upms.api.entity.SysDept;
import com.spring2go.upms.api.entity.SysMenu;
import com.spring2go.upms.api.entity.SysUserRole;
import com.spring2go.upms.biz.mapper.SysMenuMapper;
import com.spring2go.upms.biz.service.SysMenuService;
import com.spring2go.upms.biz.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @author: xiaobin
 * @date: 2021-04-08 11:01
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;

    /**
     * 查询菜单数据-下拉树结构
     *
     * @param parentId 父节点
     * @return 下拉树结构列表
     */
    @Override
    public List<MenuTree> selectMenuTree(Integer parentId) {
        Integer parent = parentId == null ? 0 : parentId;
        List<SysMenu> list = baseMapper.selectList(
                Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, parent).orderByAsc(SysMenu::getOrderNum));

        List<MenuTree> treeList = list.stream().filter(item -> !item.getMenuId().equals(item.getParentId()))
                .sorted(Comparator.comparingInt(SysMenu::getOrderNum)).map(item -> {
                    MenuTree node = new MenuTree();
                    BeanUtils.copyProperties(item, node);
                    return node;
                }).collect(Collectors.toList());
        return TreeUtils.build(treeList);
    }

    @Override
    public List<MenuTree> selectMenuTreeByRoleIds(Set<String> roleIds) {

        List<SysMenu> all = new ArrayList<>();
        roleIds.stream().forEach(roleId -> {
            all.addAll(sysMenuMapper.listMenusByRoleId(roleId));
        });

        List<MenuTree> treeList = all.stream().filter(item -> !item.getMenuId().equals(item.getParentId()))
                .sorted(Comparator.comparingInt(SysMenu::getOrderNum)).map(item -> {
                    MenuTree node = new MenuTree();
                    BeanUtils.copyProperties(item, node);
                    return node;
                }).collect(Collectors.toList());

        return TreeUtils.build(treeList);
    }
}
