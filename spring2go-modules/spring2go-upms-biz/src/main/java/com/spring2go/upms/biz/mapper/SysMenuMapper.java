package com.spring2go.upms.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring2go.upms.api.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 系统菜单
 * @author: xiaobin
 * @date: 2021/4/8 10:51
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
}
