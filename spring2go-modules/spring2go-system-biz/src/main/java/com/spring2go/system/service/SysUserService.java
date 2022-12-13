package com.spring2go.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.system.vo.UserInfo;
import com.spring2go.system.vo.UserVo;
import com.spring2go.system.entity.SysUser;
import org.apache.catalina.User;

/**
 * @description: 系统用户服务
 * @author: xiaobin
 * @date: 2021/3/30 10:23
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserInfo getUserInfoById(Long id);

    /**
     * 通过用户名获取用户信息
     *
     * @param username
     * @return com.spring2go.upms.api.dto.UserInfo
     */
    SysUser getUserInfoByName(String username);


    Boolean saveUser(UserVo userVo);

    Boolean updateUser(UserVo userVo);

    Boolean resetPwd(UserVo userVo);

    IPage getUserWithRolePage(Page page, UserVo userVo);

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param userVo 用户信息
     * @return 用户信息集合信息
     */
    IPage<SysUser> selectAllocatedList(UserVo userVo);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param userVo 用户信息
     * @return 用户信息集合信息
     */
    IPage<SysUser> selectUnallocatedList(UserVo userVo);



}
