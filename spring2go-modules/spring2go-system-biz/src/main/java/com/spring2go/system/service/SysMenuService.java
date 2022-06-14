package com.spring2go.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.system.vo.MenuTree;
import com.spring2go.system.entity.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * @description: 系统菜单
 * @author: xiaobin
 * @date: 2021/4/8 11:00
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 查询菜单数据-下拉树结构
     *
     * @param parentId 父节点
     * @return 下拉树结构列表
     */
    List<MenuTree> selectMenuTree(Integer parentId);


    /**
     * 查询角色菜单数据-下拉树结构
     *
     * @param roleIds 角色ID
     * @return 下拉树结构列表
     */
    List<MenuTree> selectMenuTreeByRoleIds(Set<String> roleIds);
}
