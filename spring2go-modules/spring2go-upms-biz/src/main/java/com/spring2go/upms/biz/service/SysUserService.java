package com.spring2go.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.upms.api.entity.SysUser;
import com.spring2go.upms.api.dto.UserInfo;

/**
 * @description: 系统用户服务
 * @author: xiaobin
 * @date: 2021/3/30 10:23
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 通过ID查询用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    UserInfo getUserInfoById(Integer id);
}
