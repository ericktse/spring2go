package com.spring2go.common.log.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @description: 日志类型
 * @author: xiaobin
 * @date: 2021/4/7 9:36
 */
@Getter
@RequiredArgsConstructor
public enum LogType {
    /**
     * 正常日志类型
     */
    NORMAL("0", "正常日志"),

    /**
     * 错误日志类型
     */
    ERROR("9", "错误日志");

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String description;
}
