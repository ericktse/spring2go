package com.spring2go.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.system.entity.SysRoleMenu;

/**
 * @description: 系统角色菜单
 * @author: xiaobin
 * @date: 2021/4/8 11:00
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

   Boolean saveRoleMenus(Integer roleId, String menuIds);
}
