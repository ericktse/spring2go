package com.spring2go.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.system.entity.SysUserRole;
import com.spring2go.system.vo.UserInfo;
import com.spring2go.system.vo.UserVo;

/**
 * @description: 系统用户服务
 * @author: xiaobin
 * @date: 2021/3/30 10:23
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param userVo 用户信息
     * @return 用户信息集合信息
     */
    IPage<UserInfo> getAllocatedPage(Page page, UserVo userVo);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param userVo 用户信息
     * @return 用户信息集合信息
     */
    IPage<UserInfo> getUnallocatedPage(Page page,UserVo userVo);

    /**
     * 取消授权用户角色
     *
     * @param roleId 角色id
     * @param userId 用户id
     * @return 结果
     */
    int deleteAuthUser(Long roleId, Long userId);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    int deleteAuthUsers(Long roleId, Long[] userIds);

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    int insertAuthUsers(Long roleId, Long[] userIds);
}
