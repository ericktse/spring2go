package com.spring2go.system.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.spring2go.common.core.annotation.Excel;
import com.spring2go.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 操作日志
 * @author: xiaobin
 * @date: 2021-04-02 16:35
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysLog extends BaseEntity {

    /**
     * 编号
     */
    @TableId
    @ApiModelProperty(value = "日志编号")
    @Excel(name = "序号")
    private Long id;

    /**
     * 日志类型
     */
    @ApiModelProperty(value = "日志类型")
    private String type;

    /**
     * 日志标题
     */
    @Excel(name = "日志标题")
    @ApiModelProperty(value = "日志标题")
    private String title;

    /**
     * 操作IP地址
     */
    @Excel(name = "IP地址")
    @ApiModelProperty(value = "IP地址")
    private String remoteAddr;

    /**
     * 用户浏览器
     */
    @Excel(name = "用户代理")
    @ApiModelProperty(value = "用户代理")
    private String userAgent;

    /**
     * 请求URI
     */
    @Excel(name = "请求URL")
    @ApiModelProperty(value = "请求URL")
    private String requestUri;

    /**
     * 操作方式
     */
    @Excel(name = "操作方式")
    @ApiModelProperty(value = "操作方式")
    private String method;

    /**
     * 请求参数
     */
    @Excel(name = "请求参数")
    @ApiModelProperty(value = "请求参数")
    private String params;

    /**
     * 返回结果
     */
    @Excel(name = "返回结果")
    @ApiModelProperty(value = "返回结果")
    private String result;

    /**
     * 执行时间
     */
    @Excel(name = "执行时间", cellType = Excel.CellType.NUMERIC)
    @ApiModelProperty(value = "执行时间")
    private Long time;

    /**
     * 异常信息
     */
    @Excel(name = "异常信息")
    @ApiModelProperty(value = "异常信息")
    private String exception;

    /**
     * 返回状态
     */
    @Excel(name = "状态", readConverterExp = "0=成功,1=失败")
    @ApiModelProperty(value = "返回状态")
    private String responseStatus;

    /**
     * 删除标志（0-代表存在 1-代表删除）
     */
    @TableLogic
    private String delFlag;

}
