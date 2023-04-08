package com.spring2go.system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典翻译model
 *
 * @author xiaobin
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class DictModel {

    private String text;

    private String value;

    private String code;
}
