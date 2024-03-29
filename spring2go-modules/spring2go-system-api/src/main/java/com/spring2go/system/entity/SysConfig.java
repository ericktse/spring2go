package com.spring2go.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.spring2go.common.core.annotation.Excel;
import com.spring2go.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 配置项
 *
 * @author xiaobin
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysConfig extends BaseEntity {
    /**
     * 参数主键
     */
    @Excel(name = "参数主键", cellType = Excel.CellType.NUMERIC)
    @TableId(type = IdType.AUTO)
    private Long configId;

    /**
     * 参数名称
     */
    @Excel(name = "参数名称")
    private String configName;

    /**
     * 参数键名
     */
    @Excel(name = "参数键名")
    private String configKey;

    /**
     * 参数键值
     */
    @Excel(name = "参数键值")
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    private String configType;

    /**
     * 删除标志（0-代表存在 1-代表删除）
     */
    @TableLogic
    private String delFlag;
}
