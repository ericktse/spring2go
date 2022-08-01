package com.spring2go.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spring2go.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 系统部门信息
 * @author: xiaobin
 * @date: 2021-03-31 17:07
 */
@Data
@TableName("sys_dept")
public class SysDepartment extends BaseEntity {

    /**
     * 部门ID
     */
    @TableId(value = "dept_id", type = IdType.AUTO)
    @ApiModelProperty(value = "部门id")
    private Long deptId;

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
     * 显示顺序
     */
    @ApiModelProperty(value = "排序值")
    private Integer orderNum;

    /**
     * 部门状态:0正常,1停用
     */
    @ApiModelProperty(value = "部门状态")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ApiModelProperty(value = "删除标志")
    @TableLogic
    private String delFlag;
}
