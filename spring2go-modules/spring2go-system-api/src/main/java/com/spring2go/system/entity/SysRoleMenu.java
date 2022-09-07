package com.spring2go.system.entity;

import com.spring2go.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 系统角色菜单
 * @author: xiaobin
 * @date: 2021-04-08 10:32
 */
@Data
public class SysRoleMenu extends BaseEntity {

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单id")
    private Integer menuId;
}
