package com.spring2go.common.rule.engine.expression;

import com.spring2go.common.rule.engine.entity.ExpressionUnit;
import com.spring2go.common.rule.engine.exception.RuleEngineException;

/**
 * 表达式引擎
 *
 * @author xiaobin
 */
public abstract class AbstractExpression {

    protected ExpressionUnit expressionUnit;

    public abstract ExpressionUnit compile(String express);

    public abstract Object execute(Object fact) throws RuleEngineException;

}
