package com.spring2go.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.upms.api.dto.MenuTree;
import com.spring2go.upms.api.entity.SysMenu;

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


    List<MenuTree> selectMenuTreeByRoleIds(Set<String> roleIds);
}
