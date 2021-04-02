package com.spring2go.upms.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.spring2go.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 操作日志
 * @author: xiaobin
 * @date: 2021-04-02 16:35
 */
@Data
public class SysLog extends BaseEntity {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "日志编号")
    private Long id;

    /**
     * 日志类型
     */
    @ApiModelProperty(value = "日志类型")
    private String type;

    /**
     * 日志标题
     */
    @ApiModelProperty(value = "日志标题")
    private String title;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 操作IP地址
     */
    @ApiModelProperty(value = "操作ip地址")
    private String remoteAddr;

    /**
     * 用户浏览器
     */
    @ApiModelProperty(value = "用户代理")
    private String userAgent;

    /**
     * 请求URI
     */
    @ApiModelProperty(value = "请求uri")
    private String requestUri;

    /**
     * 操作方式
     */
    @ApiModelProperty(value = "操作方式")
    private String method;

    /**
     * 操作提交的数据
     */
    @ApiModelProperty(value = "数据")
    private String params;

    /**
     * 执行时间
     */
    @ApiModelProperty(value = "方法执行时间")
    private Long time;

    /**
     * 异常信息
     */
    @ApiModelProperty(value = "异常信息")
    private String exception;

    /**
     * 服务ID
     */
    @ApiModelProperty(value = "应用标识")
    private String serviceId;

    /**
     * 删除标记
     */
    @TableLogic
    private String delFlag;
}
