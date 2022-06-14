package com.spring2go.system.vo;

import lombok.Data;

/**
 * 角色权限字段
 *
 * @author: xiaobin
 * @date: 2021-06-18 15:00
 */
@Data
public class RoleVo {
    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 菜单列表
     */
    private String menuIds;
}
