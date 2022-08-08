package com.spring2go.common.rule.engine.entity;

/**
 * Rule结果状态
 *
 * @author xiaobin
 */
public enum RuleResultStatus {
    /**
     * 默认
     */
    DEFAULT(0, "默认"),
    /**
     * 通过
     */
    PASSED(1, "通过"),
    /**
     * 拒绝
     */
    REJECTED(2, "拒绝");


    private Integer key;
    private String value;

    RuleResultStatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
