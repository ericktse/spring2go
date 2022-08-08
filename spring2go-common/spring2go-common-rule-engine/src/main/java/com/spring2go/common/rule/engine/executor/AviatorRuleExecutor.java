package com.spring2go.common.rule.engine.executor;

import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;
import com.spring2go.common.rule.engine.entity.RuleResultStatus;
import com.spring2go.common.rule.engine.exception.RuleEngineException;
import com.spring2go.common.rule.engine.expression.AviatorExpression;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * Aviator表达式执行器
 *
 * @author xiaobin
 */
@Slf4j
public class AviatorRuleExecutor implements RuleExecutor {
    @Override
    public Boolean check(Rule rule, Object fact) throws RuleEngineException {
        AviatorExpression aviatorExpression = new AviatorExpression();
        aviatorExpression.compile(rule.getWhenExpression());
        Object eRet = aviatorExpression.execute(fact);
        boolean ret = Boolean.parseBoolean(eRet.toString());
        return ret;
    }

    @Override
    public RuleResult execute(Rule rule, Object fact) throws RuleEngineException {
        AviatorExpression aviatorExpression = new AviatorExpression();
        aviatorExpression.compile(rule.getThenExpressionValue());
        Object value = aviatorExpression.execute(fact);

        String name = rule.getThenExpressionParam();
        if (StringUtils.isNotEmpty(name)) {
            Class<?> clazz = fact.getClass();
            try {
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                if (field.getType() == int.class || field.getType() == Integer.class) {
                    field.set(fact, Integer.parseInt(value.toString()));
                } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                    field.set(fact, Boolean.parseBoolean(value.toString()));
                } else if (field.getType() == double.class || field.getType() == Double.class) {
                    field.set(fact, Double.parseDouble(value.toString()));
                } else {
                    field.set(fact, value);
                }
            } catch (Exception e) {
                log.error("rule set execute field error: " + name, e);
            }
        }

        RuleResult result = new RuleResult(rule);
        result.setStatus(RuleResultStatus.PASSED);
        return result;
    }

}
