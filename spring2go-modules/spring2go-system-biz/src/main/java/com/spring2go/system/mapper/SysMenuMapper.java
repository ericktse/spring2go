package com.spring2go.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring2go.system.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 系统菜单
 * @author: xiaobin
 * @date: 2021/4/8 10:51
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 通过角色编号查询菜单
     *
     * @param roleId 角色ID
     * @return
     */
    List<SysMenu> listMenusByRoleId(String roleId);
}
