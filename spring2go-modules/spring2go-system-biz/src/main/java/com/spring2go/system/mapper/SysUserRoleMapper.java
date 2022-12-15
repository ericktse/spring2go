package com.spring2go.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.system.entity.SysUserRole;
import com.spring2go.system.vo.UserInfo;
import com.spring2go.system.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 系统用户角色
 * @author: xiaobin
 * @date: 2021-03-30 10:08
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据条件分页查询已配用户角色列表
     *
     * @param userVo 用户信息
     * @return 用户信息集合信息
     */
    IPage<UserInfo> getAllocatedPage(Page page, @Param("query") UserVo userVo);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param userVo 用户信息
     * @return 用户信息集合信息
     */
    IPage<UserInfo> getUnallocatedPage(Page page, @Param("query") UserVo userVo);

    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    int batchInsert(List<SysUserRole> userRoleList);
}
