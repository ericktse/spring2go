package com.spring2go.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.system.entity.SysRole;
import com.spring2go.system.vo.RoleVo;
import com.spring2go.system.vo.UserVo;

import java.util.List;
import java.util.Set;

/**
 * @description: 系统角色
 * @author: xiaobin
 * @date: 2021/4/8 11:00
 */
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> getRoleByUserName(String username);

    Set<String> getPermsByUserName(String username);

    IPage getPage(Page page, RoleVo roleVo);

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    int authDataScope(RoleVo role);
}
