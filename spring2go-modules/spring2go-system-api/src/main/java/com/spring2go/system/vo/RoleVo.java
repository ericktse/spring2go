package com.spring2go.system.vo;

import com.spring2go.system.entity.SysRole;
import lombok.Data;

/**
 * 角色权限字段
 *
 * @author: xiaobin
 * @date: 2021-06-18 15:00
 */
@Data
public class RoleVo extends SysRole {

    /**
     * 菜单列表
     */
    private String menuIds;

    private String beginTime;

    private String endTime;

    /** 部门组（数据权限） */
    private Long[] deptIds;
}
