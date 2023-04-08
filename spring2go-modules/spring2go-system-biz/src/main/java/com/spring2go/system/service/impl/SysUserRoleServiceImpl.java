package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.system.entity.SysRoleDept;
import com.spring2go.system.entity.SysUserRole;
import com.spring2go.system.mapper.SysUserRoleMapper;
import com.spring2go.system.service.SysUserRoleService;
import com.spring2go.system.vo.UserInfo;
import com.spring2go.system.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 系统用户服务
 * @author: xiaobin
 * @date: 2021-03-30 10:24
 */
@Service
@RequiredArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public IPage<UserInfo> getAllocatedPage(Page page, UserVo userVo) {
        return baseMapper.getAllocatedPage(page, userVo);
    }

    @Override
    public IPage<UserInfo> getUnallocatedPage(Page page, UserVo userVo) {
        return baseMapper.getUnallocatedPage(page, userVo);
    }

    /**
     * 取消授权用户角色
     *
     * @param roleId 角色id
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public int deleteAuthUser(Long roleId, Long userId) {
        LambdaQueryWrapper<SysUserRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUserRole::getRoleId, roleId);
        wrapper.eq(SysUserRole::getUserId, userId);

        return baseMapper.delete(wrapper);
    }

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    @Override
    public int deleteAuthUsers(Long roleId, Long[] userIds) {
        LambdaQueryWrapper<SysUserRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUserRole::getRoleId, roleId);
        wrapper.in(SysUserRole::getUserId, (Object[]) userIds);

        return baseMapper.delete(wrapper);
    }

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要授权的用户数据ID
     * @return 结果
     */
    @Override
    public int insertAuthUsers(Long roleId, Long[] userIds) {
        // 新增用户与角色管理
        List<SysUserRole> list = new ArrayList<SysUserRole>();
        for (Long userId : userIds) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return baseMapper.batchInsert(list);
    }
}
