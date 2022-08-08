package com.spring2go.common.rule.engine.command;

import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.exception.RuleEngineException;

public class DemoRuleExpressionSubRuleCheckCommand implements RuleWhenCommand {
    @Override
    public Boolean check(Rule rule, Object fact) throws RuleEngineException {
        System.out.println("DemoRuleExpressionSubRuleCheckCommand check,rule id:" + rule.getId());
        return true;
    }
}
