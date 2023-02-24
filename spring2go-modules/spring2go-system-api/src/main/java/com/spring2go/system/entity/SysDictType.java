package com.spring2go.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.spring2go.common.core.annotation.Excel;
import com.spring2go.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 字典类型
 *
 * @author xiaobin
 */
@Data
public class SysDictType extends BaseEntity {
    /**
     * 字典主键
     */
    @Excel(name = "字典主键", cellType = Excel.CellType.NUMERIC)
    @TableId(type = IdType.AUTO)
    private Long dictId;

    /**
     * 字典名称
     */
    @Excel(name = "字典名称")
    private String dictName;

    /**
     * 字典类型
     */
    @Excel(name = "字典类型")
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;
}
