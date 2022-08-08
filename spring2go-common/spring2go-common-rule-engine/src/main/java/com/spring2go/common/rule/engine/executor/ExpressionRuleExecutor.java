package com.spring2go.common.rule.engine.executor;

import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;
import com.spring2go.common.rule.engine.exception.RuleEngineException;
import com.spring2go.common.rule.engine.expression.RuleExpression;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Class规则执行器
 *
 * @author xiaobin
 */
@Slf4j
public class ExpressionRuleExecutor extends AbstractRuleExecutor {
    private RuleExpression ruleExpression;

    @Override
    public Boolean check(Rule rule, Object object) throws RuleEngineException {

        if (null == ruleExpression) {
            ruleExpression = new RuleExpression();
            ruleExpression.compile(rule.getWhenRuleExpression());
        }

        Boolean exeRet = ruleExpression.check(object);

        //TODO：如果是重复执行.

        return exeRet;
    }

    @Override
    public RuleResult execute(Rule rule, Object object) throws RuleEngineException {

        if (null == ruleExpression) {
            ruleExpression = new RuleExpression();
            ruleExpression.compile(rule.getWhenRuleExpression());
        }
        List<RuleResult> results = ruleExpression.execute(object);
        RuleResult result = new RuleResult(rule);
        result.setItemResults(results);
        return result;
    }
}
