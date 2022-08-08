package com.spring2go.common.rule.engine.executor;

import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;
import com.spring2go.common.rule.engine.exception.RuleEngineException;
import com.spring2go.common.rule.engine.expression.RuleExpression;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Class规则执行器
 *
 * @author xiaobin
 */
@Slf4j
public class ExpressionRuleExecutor implements RuleExecutor {
    @Override
    public Boolean check(Rule rule, Object fact) throws RuleEngineException {
        RuleExpression ruleExpression = new RuleExpression();
        ruleExpression.compile(rule.getWhenRuleExpression());
        Object eRet = ruleExpression.execute(fact);
        boolean ret = Boolean.parseBoolean(eRet.toString());
        return ret;
    }

    @Override
    public RuleResult execute(Rule rule, Object fact) throws RuleEngineException {
        //TODO: rule expression 暂不实现execute
        return null;
    }
}
