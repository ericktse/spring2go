package com.spring2go.system.vo;

import com.spring2go.system.entity.SysUser;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户
 *
 * @author: xiaobin
 * @date: 2021-06-17 18:06
 */
@Data
public class UserVo extends SysUser {

    private List<Long> role;

    private String beginTime;

    private String endTime;

    private Long deptId;

    private String oldPassword;

    private String newPassword;
}
