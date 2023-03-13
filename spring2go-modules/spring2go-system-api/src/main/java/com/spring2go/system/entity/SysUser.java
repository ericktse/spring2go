package com.spring2go.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.spring2go.common.core.annotation.Excel;
import com.spring2go.common.core.domain.BaseEntity;
import com.spring2go.common.core.annotation.Dict;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description: 系统用户
 * @author: xiaobin
 * @date: 2021-03-30 10:09
 */
@Data
public class SysUser extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @Excel(name = "用户序号", cellType = Excel.CellType.NUMERIC, prompt = "用户编号")
    private Long userId;

    /**
     * 部门ID
     */
    @Dict(table = "sys_dept", code = "dept_id", text = "dept_name")
    private Long deptId;

//    /**
//     * 岗位ID
//     */
//    private Integer postId;

    /**
     * 用户名
     */
    @Excel(name = "用户名称")
    private String userName;

    /**
     * 昵称
     */
    @Excel(name = "用户昵称")
    private String nickName;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    private String email;

    /**
     * 手机号
     */
    @Excel(name = "手机号码")
    private String phone;

    /**
     * 性别
     */
    @Dict(code = "sys_user_sex")
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 删除标志（0-代表存在 1-代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 最后登录IP
     */
    @Excel(name = "最后登录IP", operateType = Excel.OperateType.EXPORT)
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", operateType = Excel.OperateType.EXPORT)
    private Date loginDate;

}
