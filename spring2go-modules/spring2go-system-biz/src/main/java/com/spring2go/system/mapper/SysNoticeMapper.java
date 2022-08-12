package com.spring2go.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring2go.system.entity.SysNotice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统通知
 *
 * @author xiaobin
 */
@Mapper
public interface SysNoticeMapper extends BaseMapper<SysNotice> {
}
