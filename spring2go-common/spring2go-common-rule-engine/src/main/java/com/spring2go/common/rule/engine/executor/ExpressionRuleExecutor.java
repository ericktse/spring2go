package com.spring2go.common.rule.engine.executor;

import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;
import com.spring2go.common.rule.engine.exception.RuleEngineException;
import com.spring2go.common.rule.engine.expression.RuleExpression;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 子规则表达式规则执行器
 *
 * @author xiaobin
 */
@Slf4j
public class ExpressionRuleExecutor implements RuleExecutor {
    private Map<String, RuleExpression> ruleExpressions = new HashMap<>();

    @Override
    public Boolean check(Rule rule, Object fact) throws RuleEngineException {
        RuleExpression ruleExpression = null;
        if (ruleExpressions.containsKey(rule.getId())) {
            ruleExpression = ruleExpressions.get(rule.getId());
        } else {
            ruleExpression = new RuleExpression();
            ruleExpression.compile(rule.getWhenRuleExpression());
            ruleExpressions.put(rule.getId(), ruleExpression);
        }
        Object eRet = ruleExpression.execute(fact);
        boolean ret = Boolean.parseBoolean(eRet.toString());
        return ret;
    }

    @Override
    public RuleResult execute(Rule rule, Object fact) throws RuleEngineException {
        RuleExpression ruleExpression = null;
        if (ruleExpressions.containsKey(rule.getId())) {
            ruleExpression = ruleExpressions.get(rule.getId());
        } else {
            ruleExpression = new RuleExpression();
            ruleExpression.compile(rule.getWhenRuleExpression());
            ruleExpressions.put(rule.getId(), ruleExpression);
        }

        List<RuleResult> results = new ArrayList<>();
        ruleExpression.executeSub(null, fact, results);

        RuleResult result = new RuleResult(rule);
        result.setItemResults(results);
        return result;
    }
}
