package com.spring2go.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.system.entity.SysDictType;
import com.spring2go.system.vo.DictVo;

/**
 * 字典数据
 *
 * @author xiaobin
 */
public interface SysDictTypeService extends IService<SysDictType> {
    IPage getPage(Page page, DictVo dictVo);
}
