package com.spring2go.upms.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: 日志查询对象
 * @author: xiaobin
 * @date: 2021-04-07 10:24
 */
@Data
@ApiModel(value = "日志查询对象")
public class SysLogDto {
    /**
     * 查询日志类型
     */
    @ApiModelProperty(value = "日志类型")
    private String type;

    /**
     * 创建时间区间 [开始时间，结束时间]
     */
    @ApiModelProperty(value = "创建时间区间")
    private LocalDateTime[] createTime;
}
