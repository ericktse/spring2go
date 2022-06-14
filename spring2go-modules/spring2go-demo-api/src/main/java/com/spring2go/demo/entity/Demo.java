package com.spring2go.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.spring2go.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * demo实体
 *
 * @author xiaobin
 */
@Data
public class Demo extends BaseEntity {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * Name
     */
    @ApiModelProperty(value = "name")
    private String name;
}
