package com.spring2go.common.rule.engine.expression;

import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;
import com.spring2go.common.rule.engine.exception.RuleEngineException;
import com.spring2go.common.rule.engine.executor.SimpleRuleExecutor;
import com.spring2go.common.rule.engine.util.RuleCacheUtils;
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
    private boolean value = false;
    private String name = null;
    private List<OperationUnit> leftSubList = null;
    private List<OperationUnit> rightSubList = null;

    public boolean check(Object fact) throws RuleEngineException {
        if (this.type == OperationType.NOT) {
            value = !right.check(fact);
        } else if (this.type == OperationType.AND) {
            boolean temp = left.check(fact);
            if (!temp) {
                value = false;
            } else {
                value = temp && right.check(fact);
            }
        } else if (this.type == OperationType.OR) {
            boolean temp = left.check(fact);
            if (temp) {
                value = true;
            } else {
                value = temp || right.check(fact);
            }
        } else if (this.type == OperationType.VARIABLE) {
            //如果是规则变量，则执行规则计算
            Rule rule = RuleCacheUtils.getRule(operator);
            if (null != rule) {
                SimpleRuleExecutor ruleExecutor = new SimpleRuleExecutor();
                value = ruleExecutor.check(rule, fact);
            }
        }
        return value;
    }

    public void execute(Object fact, List<RuleResult> results) throws RuleEngineException {
        //如果是规则变量，则执行规则计算
        if (this.type == OperationType.VARIABLE) {
            if (value) {
                Rule rule = RuleCacheUtils.getRule(operator);
                if (null != rule) {
                    SimpleRuleExecutor ruleExecutor = new SimpleRuleExecutor();
                    RuleResult ret = ruleExecutor.execute(rule, fact);
                    if (null != ret) {
                        results.add(ret);
                    }
                }
            }
        } else {
            left.execute(fact, results);
            right.execute(fact, results);
        }
    }
}
