package com.spring2go.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.common.core.domain.R;
import com.spring2go.system.entity.SysDictData;
import com.spring2go.system.vo.DictModel;
import com.spring2go.system.vo.DictVo;

import java.util.List;

/**
 * 字典数据
 *
 * @author xiaobin
 */
public interface SysDictDataService extends IService<SysDictData> {

    List<SysDictData> selectDictDataByType(String dictType);

    IPage getPage(Page page, DictVo dictVo);

    List<DictModel> queryDict(String code);

    List<DictModel> queryDictFromTable(String table, String code, String text,String value);
}
