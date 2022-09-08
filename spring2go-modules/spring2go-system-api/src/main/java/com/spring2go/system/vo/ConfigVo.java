package com.spring2go.system.vo;

import com.spring2go.system.entity.SysConfig;
import lombok.Data;

/**
 * TODO
 *
 * @author xiaobin
 */
@Data
public class ConfigVo extends SysConfig {
    private String beginTime;

    private String endTime;
}
