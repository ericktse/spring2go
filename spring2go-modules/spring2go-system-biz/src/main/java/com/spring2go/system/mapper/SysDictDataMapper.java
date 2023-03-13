package com.spring2go.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring2go.system.entity.SysDictData;
import com.spring2go.system.vo.DictModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典表 数据层
 *
 * @author xiaobin
 */
@Mapper
public interface SysDictDataMapper extends BaseMapper<SysDictData> {
    List<DictModel> queryDict(@Param("code") String code);

    List<DictModel> queryDictFromTable(@Param("table") String table, @Param("code") String code, @Param("text") String text, @Param("value") String value);
}
