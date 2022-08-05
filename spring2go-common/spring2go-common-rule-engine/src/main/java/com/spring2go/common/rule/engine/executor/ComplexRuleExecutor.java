package com.spring2go.common.rule.engine.executor;

import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;
import com.spring2go.common.rule.engine.exception.RuleEngineException;
import com.spring2go.common.rule.engine.expression.RuleExpression;

import java.util.List;

/**
 * 复杂规则执行器
 *
 * @author xiaobin
 */
public class ComplexRuleExecutor extends AbstractRuleExecutor {
    private RuleExpression ruleExpression;

    @Override
    public Boolean check(Rule rule, Object object) throws RuleEngineException {

        //子规则嵌套表达式
        if (StringUtils.isNotEmpty(rule.getWhenRuleExpression())) {

            if (null == ruleExpression) {
                ruleExpression = new RuleExpression();
                ruleExpression.compile(rule.getWhenRuleExpression());
            }

            Boolean exeRet = ruleExpression.check(object);

            //TODO：如果是重复执行.

            return exeRet;

        } else {
            SimpleRuleExecutor executor = new SimpleRuleExecutor();
            return executor.check(rule, object);
        }
    }

    @Override
    public RuleResult execute(Rule rule, Object object) throws RuleEngineException {

        if (StringUtils.isNotEmpty(rule.getWhenRuleExpression())) {

            if (null == ruleExpression) {
                ruleExpression = new RuleExpression();
                ruleExpression.compile(rule.getWhenRuleExpression());
            }
            List<RuleResult> results = ruleExpression.execute(object);
            RuleResult result = new RuleResult(rule);
            result.setItemResults(results);
            return result;

        } else {
            SimpleRuleExecutor executor = new SimpleRuleExecutor();
            return executor.execute(rule, object);
        }
    }
}
