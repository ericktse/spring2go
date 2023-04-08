package com.spring2go.system.vo;

import com.spring2go.system.entity.SysDictType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TODO
 *
 * @author xiaobin
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class DictVo extends SysDictType {

    private String beginTime;

    private String endTime;

    private String dictLabel;
}
