package com.spring2go.upms.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 部门查询对象
 * @author: xiaobin
 * @date: 2021-04-08 9:57
 */
@Data
@ApiModel(value = "部门查询对象")
public class SysDeptDto {

    /**
     * 父部门ID
     */
    @ApiModelProperty(value = "父级部门id")
    private Long parentId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /**
     * 部门状态:0正常,1停用
     */
    @ApiModelProperty(value = "部门状态")
    private String status;
}
