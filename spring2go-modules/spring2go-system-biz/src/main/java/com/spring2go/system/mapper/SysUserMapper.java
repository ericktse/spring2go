package com.spring2go.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.system.dto.UserDTO;
import com.spring2go.system.entity.SysUser;
import com.spring2go.system.dto.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 系统用户Mapper
 * @author: xiaobin
 * @date: 2021-03-30 10:08
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return userVo
     */
    UserInfo getUserInfoById(Integer id);

    /**
     * 分页查询用户信息（含角色）
     * @param page 分页
     * @param userDTO 查询参数
     * @return list
     */
    IPage<UserInfo> getUserWithRolePage(Page page, @Param("query") UserDTO userDTO);
}
