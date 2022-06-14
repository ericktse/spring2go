package com.spring2go.system.vo;

import com.spring2go.system.entity.SysUser;
import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author: xiaobin
 * @date: 2021-06-17 18:06
 */
@Data
public class UserVo extends SysUser {

    private List<Integer> role;

    private Integer deptId;
}
