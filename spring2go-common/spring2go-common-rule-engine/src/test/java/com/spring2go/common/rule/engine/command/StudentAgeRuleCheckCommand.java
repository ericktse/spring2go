package com.spring2go.common.rule.engine.command;

import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.exception.RuleEngineException;

public class StudentAgeRuleCheckCommand implements RuleWhenCommand {
    @Override
    public Boolean check(Rule rule, Object fact) throws RuleEngineException {
        System.out.println("command check,rule id" + rule.getId());
        return true;
    }
}
