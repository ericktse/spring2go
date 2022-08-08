package com.spring2go.common.rule.engine.executor;

import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;
import com.spring2go.common.rule.engine.exception.RuleEngineException;


/**
 * 复杂规则执行器
 *
 * @author xiaobin
 */
public class ComplexRuleExecutor extends AbstractRuleExecutor {
    private ExpressionRuleExecutor expressionRuleExecutor = null;

    @Override
    public Boolean check(Rule rule, Object object) throws RuleEngineException {

        if (StringUtils.isNotEmpty(rule.getWhenRuleExpression())) {
            if (null == expressionRuleExecutor) {
                expressionRuleExecutor = new ExpressionRuleExecutor();
            }
            return expressionRuleExecutor.check(rule, object);
        }

        if (StringUtils.isNotEmpty(rule.getWhenClass())) {
            AbstractRuleExecutor ruleExecutor = new ClassRuleExecutor();
            return ruleExecutor.check(rule, object);
        }

        if (StringUtils.isNotEmpty(rule.getWhenSql())) {
            AbstractRuleExecutor ruleExecutor = new SqlRuleExecutor();
            return ruleExecutor.check(rule, object);
        }

        return false;
    }

    @Override
    public RuleResult execute(Rule rule, Object object) throws RuleEngineException {

        if (StringUtils.isNotEmpty(rule.getWhenRuleExpression())) {
            if (null == expressionRuleExecutor) {
                expressionRuleExecutor = new ExpressionRuleExecutor();
            }
            return expressionRuleExecutor.execute(rule, object);
        }

        if (StringUtils.isNotEmpty(rule.getWhenClass())) {
            AbstractRuleExecutor ruleExecutor = new ClassRuleExecutor();
            return ruleExecutor.execute(rule, object);
        }

        if (StringUtils.isNotEmpty(rule.getWhenSql())) {
            AbstractRuleExecutor ruleExecutor = new SqlRuleExecutor();
            return ruleExecutor.execute(rule, object);
        }

        return null;
    }
}
