package com.spring2go.system.vo;

import com.spring2go.system.entity.SysConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 配置vo
 *
 * @author xiaobin
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ConfigVo extends SysConfig {
    private String beginTime;

    private String endTime;
}
