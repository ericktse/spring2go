package com.spring2go.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.system.vo.MenuTree;
import com.spring2go.system.entity.SysMenu;
import com.spring2go.system.vo.RouterVo;

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
     * @param roleNames 角色名称
     * @return 下拉树结构列表
     */
    List<MenuTree> selectMenuTreeByRoleNames(Set<String> roleNames);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<RouterVo> buildMenus(List<MenuTree> menus);
}
