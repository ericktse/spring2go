package com.spring2go.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring2go.system.entity.SysDepartment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 系统部门信息Mapper
 * @author: xiaobin
 * @date: 2021-03-31 17:10
 */
@Mapper
public interface SysDepartmentMapper extends BaseMapper<SysDepartment> {
}
