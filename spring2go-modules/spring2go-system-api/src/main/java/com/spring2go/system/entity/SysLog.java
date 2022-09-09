package com.spring2go.system.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.spring2go.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    @TableId
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
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String params;

    /**
     * 返回结果
     */
    @ApiModelProperty(value = "返回结果")
    private String result;

    /**
     * 执行时间
     */
    @ApiModelProperty(value = "执行时间")
    private Long time;

    /**
     * 异常信息
     */
    @ApiModelProperty(value = "异常信息")
    private String exception;

    /**
     * 返回状态
     */
    @ApiModelProperty(value = "返回状态")
    private String responseStatus;

}
