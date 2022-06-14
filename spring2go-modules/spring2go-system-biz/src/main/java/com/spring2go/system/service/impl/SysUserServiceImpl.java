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
import com.spring2go.system.dto.UserDTO;
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
     * @param userDto DTO 对象
     * @return success/fail
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUser(UserDTO userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setDelFlag(CommonConstants.NORMAL);
        sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        baseMapper.insert(sysUser);
        List<SysUserRole> userRoleList = userDto.getRole().stream().map(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            return userRole;
        }).collect(Collectors.toList());
        return sysUserRoleService.saveBatch(userRoleList);
    }


    @Override
    public Boolean updateUser(UserDTO userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setUpdateTime(DateUtils.now());

        if (StringUtils.isNotEmpty(userDto.getPassword())) {
            sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        }
        this.updateById(sysUser);

        sysUserRoleService
                .remove(Wrappers.<SysUserRole>update().lambda().eq(SysUserRole::getUserId, userDto.getUserId()));

        List<SysUserRole> userRoleList = userDto.getRole().stream().map(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            return userRole;
        }).collect(Collectors.toList());
        return sysUserRoleService.saveBatch(userRoleList);
    }

    /**
     * 分页查询用户信息（含有角色信息）
     * @param page 分页对象
     * @param userDTO 参数列表
     * @return
     */
    @Override
    public IPage getUserWithRolePage(Page page, UserDTO userDTO) {
        return baseMapper.getUserWithRolePage(page, userDTO);
    }
}
