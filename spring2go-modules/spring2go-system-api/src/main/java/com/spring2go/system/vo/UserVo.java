package com.spring2go.system.vo;

import com.spring2go.system.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 用户
 *
 * @author: xiaobin
 * @date: 2021-06-17 18:06
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserVo extends SysUser {

    private List<Long> role;

    private Long roleId;

    private String oldPassword;

    private String newPassword;

    private String createTimeBegin;

    private String createTimeEnd;
}
