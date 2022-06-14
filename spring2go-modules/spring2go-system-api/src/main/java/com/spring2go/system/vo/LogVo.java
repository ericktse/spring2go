package com.spring2go.system.vo;

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
public class LogVo {
    /**
     * 查询日志类型
     */
    @ApiModelProperty(value = "日志类型")
    private String type;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;
}
