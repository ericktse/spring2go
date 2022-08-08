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
public class ComplexRuleExecutor implements RuleExecutor {
    private ExpressionRuleExecutor expressionRuleExecutor = null;

    @Override
    public Boolean check(Rule rule, Object fact) throws RuleEngineException {

        if (StringUtils.isNotEmpty(rule.getWhenRuleExpression())) {
            if (null == expressionRuleExecutor) {
                expressionRuleExecutor = new ExpressionRuleExecutor();
            }
            return expressionRuleExecutor.check(rule, fact);
        }

        if (StringUtils.isNotEmpty(rule.getWhenExpression())) {
            RuleExecutor ruleExecutor = new AviatorRuleExecutor();
            return ruleExecutor.check(rule, fact);
        }

        if (StringUtils.isNotEmpty(rule.getWhenClass())) {
            RuleExecutor ruleExecutor = new ClassRuleExecutor();
            return ruleExecutor.check(rule, fact);
        }

        if (StringUtils.isNotEmpty(rule.getWhenSql())) {
            RuleExecutor ruleExecutor = new SqlRuleExecutor();
            return ruleExecutor.check(rule, fact);
        }

        return false;
    }

    @Override
    public RuleResult execute(Rule rule, Object fact) throws RuleEngineException {

        RuleResult ruleResult = new RuleResult(rule);

        //是否允许规则嵌套执行
        if (StringUtils.isNotEmpty(rule.getWhenRuleExpression())
                && rule.getWhenRuleExpressionLoop()) {
            if (null == expressionRuleExecutor) {
                expressionRuleExecutor = new ExpressionRuleExecutor();
            }
            ruleResult.addItemResult(expressionRuleExecutor.execute(rule, fact));
        }

        if (StringUtils.isNotEmpty(rule.getThenExpressionValue())) {
            RuleExecutor ruleExecutor = new AviatorRuleExecutor();
            ruleResult.addItemResult(ruleExecutor.execute(rule, fact));
        }

        if (StringUtils.isNotEmpty(rule.getThenClass())) {
            RuleExecutor ruleExecutor = new ClassRuleExecutor();
            ruleResult.addItemResult(ruleExecutor.execute(rule, fact));
        }

        if (StringUtils.isNotEmpty(rule.getThenSql())) {
            RuleExecutor ruleExecutor = new SqlRuleExecutor();
            ruleResult.addItemResult(ruleExecutor.execute(rule, fact));
        }

        return ruleResult;
    }
}
