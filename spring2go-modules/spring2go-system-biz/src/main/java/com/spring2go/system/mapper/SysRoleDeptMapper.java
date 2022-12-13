package com.spring2go.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring2go.system.entity.SysRoleDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 系统角色菜单
 * @author: xiaobin
 * @date: 2021/4/8 10:51
 */
@Mapper
public interface SysRoleDeptMapper extends BaseMapper<SysRoleDept> {

    /**
     * 批量新增角色部门信息
     *
     * @param roleDeptList 角色部门列表
     * @return 结果
     */
     int batchRoleDept(List<SysRoleDept> roleDeptList);

}
