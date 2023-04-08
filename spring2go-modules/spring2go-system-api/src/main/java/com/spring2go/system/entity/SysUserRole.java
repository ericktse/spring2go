package com.spring2go.system.entity;

import com.spring2go.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description: 用户角色
 * @author: xiaobin
 * @date: 2021-04-08 10:33
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysUserRole extends BaseEntity {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;
}
