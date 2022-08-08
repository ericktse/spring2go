package com.spring2go.common.rule.engine.entity;

import lombok.Data;

import java.util.List;

/**
 * 表达式单元
 *
 * @author xiaobin
 */
@Data
public class ExpressionUnit {
    private ExpressionUnit left = null;
    private ExpressionUnit right = null;
    private String type;
    private String operator = null;
    private Boolean value = false;
    private String name = null;
    private List<OperationUnit> leftSubList = null;
    private List<OperationUnit> rightSubList = null;
    private Object CompiledExpression;
}
