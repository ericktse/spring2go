package com.spring2go.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.spring2go.common.core.annotation.Excel;
import com.spring2go.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 系统角色
 * @author: xiaobin
 * @date: 2021-04-08 10:18
 */
@Data
public class SysRole extends BaseEntity {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "角色编号")
    @Excel(name = "角色序号", cellType = Excel.CellType.NUMERIC)
    private Long roleId;

    @Excel(name = "角色名称")
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @Excel(name = "角色权限")
    @ApiModelProperty(value = "角色标识")
    private String roleKey;

    @Excel(name = "角色排序")
    @ApiModelProperty(value = "角色排序")
    private Integer roleSort;

    /**
     * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限）
     */
    @Excel(name = "数据范围", readConverterExp = "1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限,5=仅本人数据权限")
    @ApiModelProperty(value = "数据范围")
    private String dataScope;

    /**
     * 角色状态（0正常 1停用）
     */
    @Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty(value = "角色状态")
    private String status;


    /**
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    private Boolean menuCheckStrictly;

    /**
     * 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ）
     */
    private Boolean deptCheckStrictly;

    /**
     * 删除标志（0-代表存在 1-代表删除）
     */
    @TableLogic
    private String delFlag;
}
