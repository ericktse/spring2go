package com.spring2go.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.system.entity.SysDictData;

import java.util.List;

/**
 * 字典数据
 *
 * @author xiaobin
 */
public interface SysDictDataService extends IService<SysDictData> {

    List<SysDictData> selectDictDataByType(String dictType);
}
