package com.spring2go.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring2go.system.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 系统角色
 * @author: xiaobin
 * @date: 2021/4/8 10:51
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> getRoleByUserName(String username);
}
