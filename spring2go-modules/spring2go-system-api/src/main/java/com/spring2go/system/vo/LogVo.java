package com.spring2go.system.vo;

import com.spring2go.common.core.constant.PageConstants;
import com.spring2go.system.entity.SysLog;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Description: 日志查询对象
 * @author: xiaobin
 * @date: 2021-04-07 10:24
 */
@Data
@ApiModel(value = "日志查询对象")
public class LogVo extends SysLog {

    private String beginTime;

    private String endTime;

    private Integer pageNum = PageConstants.DEFAULT_PAGE_NO;

    private Integer pageSize = PageConstants.DEFAULT_PAGE_SIZE;
}
