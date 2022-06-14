package com.spring2go.system.dto;

import lombok.Data;

/**
 * 角色权限字段
 *
 * @author: xiaobin
 * @date: 2021-06-18 15:00
 */
@Data
public class RoleDTO {
    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 菜单列表
     */
    private String menuIds;
}
