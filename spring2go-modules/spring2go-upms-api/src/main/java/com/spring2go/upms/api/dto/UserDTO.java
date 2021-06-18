package com.spring2go.upms.api.dto;

import com.spring2go.upms.api.entity.SysUser;
import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author: xiaobin
 * @date: 2021-06-17 18:06
 */
@Data
public class UserDTO extends SysUser {

    private List<Integer> role;

    private Integer deptId;
}
