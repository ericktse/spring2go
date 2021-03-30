package com.spring2go.upms.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring2go.upms.api.entity.SysUser;
import com.spring2go.upms.api.dto.UserInfo;
import org.apache.ibatis.annotations.Mapper;

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
}
