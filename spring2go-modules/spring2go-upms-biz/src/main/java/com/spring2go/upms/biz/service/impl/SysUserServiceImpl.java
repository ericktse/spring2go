package com.spring2go.upms.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.upms.api.entity.SysUser;
import com.spring2go.upms.api.dto.UserInfo;
import com.spring2go.upms.biz.mapper.SysUserMapper;
import com.spring2go.upms.biz.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Description: 系统用户服务
 * @author: xiaobin
 * @date: 2021-03-30 10:24
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public UserInfo getUserInfoById(Integer id) {
        return baseMapper.getUserInfoById(id);
    }

}
