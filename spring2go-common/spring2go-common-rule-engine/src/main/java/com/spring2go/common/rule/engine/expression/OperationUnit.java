package com.spring2go.common.rule.engine.expression;

import lombok.Data;

/**
 * 操作表达式单元
 *
 * @author xiaobin
 */
@Data
public class OperationUnit {
    private String type;
    private String element;
    private int level = 0;

    public OperationUnit() {
    }

    public OperationUnit(String element, String type, int level) {
        this.element = element;
        this.type = type;
        this.level = level;
    }
}