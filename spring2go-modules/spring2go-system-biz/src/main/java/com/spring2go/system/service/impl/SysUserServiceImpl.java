package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.common.core.constant.CommonConstants;
import com.spring2go.common.core.util.DateUtils;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.system.mapper.SysUserMapper;
import com.spring2go.system.service.SysUserRoleService;
import com.spring2go.system.vo.UserVo;
import com.spring2go.system.entity.SysUser;
import com.spring2go.system.entity.SysUserRole;
import com.spring2go.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 系统用户服务
 * @author: xiaobin
 * @date: 2021-03-30 10:24
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();


    private final SysUserRoleService sysUserRoleService;

    @Override
    public SysUser getUserInfoById(Integer id) {
        return baseMapper.selectById(id);
    }


    @Override
    public SysUser getUserInfoByName(String username) {
        return baseMapper.selectOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUserName, username));
    }

    /**
     * 保存用户信息
     *
     * @param userVo DTO 对象
     * @return success/fail
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUser(UserVo userVo) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userVo, sysUser);
        sysUser.setDelFlag(CommonConstants.NORMAL);
        sysUser.setPassword(ENCODER.encode(userVo.getPassword()));
        sysUser.setCreateTime(DateUtils.now());
        int result = baseMapper.insert(sysUser);
        if (StringUtils.isNotEmpty(userVo.getRole())) {
            List<SysUserRole> userRoleList = userVo.getRole().stream().map(roleId -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(sysUser.getUserId());
                userRole.setRoleId(roleId);
                return userRole;
            }).collect(Collectors.toList());
            return sysUserRoleService.saveBatch(userRoleList);
        }

        return result == 1;
    }


    @Transactional
    @Override
    public Boolean updateUser(UserVo userVo) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userVo, sysUser);
        sysUser.setUpdateTime(DateUtils.now());

        if (StringUtils.isNotEmpty(userVo.getPassword())) {
            sysUser.setPassword(ENCODER.encode(userVo.getPassword()));
        }
        boolean result = this.updateById(sysUser);

        sysUserRoleService
                .remove(Wrappers.<SysUserRole>update().lambda().eq(SysUserRole::getUserId, userVo.getUserId()));

        if (StringUtils.isNotEmpty(userVo.getRole())) {
            List<SysUserRole> userRoleList = userVo.getRole().stream().map(roleId -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(sysUser.getUserId());
                userRole.setRoleId(roleId);
                return userRole;
            }).collect(Collectors.toList());
            result = sysUserRoleService.saveBatch(userRoleList);
        }
        return result;
    }

    @Override
    public Boolean resetPwd(UserVo userVo) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userVo, sysUser);
        sysUser.setUpdateTime(DateUtils.now());

        if (StringUtils.isNotEmpty(userVo.getPassword())) {
            sysUser.setPassword(ENCODER.encode(userVo.getPassword()));
        }
        return this.updateById(sysUser);
    }

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param page   分页对象
     * @param userVo 参数列表
     * @return
     */
    @Override
    public IPage getUserWithRolePage(Page page, UserVo userVo) {
        return baseMapper.getUserWithRolePage(page, userVo);
    }
}
