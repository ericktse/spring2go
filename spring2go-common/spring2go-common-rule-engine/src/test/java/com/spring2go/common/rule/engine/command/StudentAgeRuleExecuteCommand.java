package com.spring2go.common.rule.engine.command;

import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;

public class StudentAgeRuleExecuteCommand implements RuleThenCommand {

    @Override
    public RuleResult execute(Rule rule, Object fact) {
        System.out.println("command execute,rule id" + rule.getId());

        RuleResult result = new RuleResult(rule);
        return result;
    }
}
