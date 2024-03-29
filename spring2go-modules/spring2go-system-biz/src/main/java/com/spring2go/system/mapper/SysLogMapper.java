package com.spring2go.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring2go.system.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 系统日志Mapper
 * @author: xiaobin
 * @date: 2021-04-02 16:40
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
}
