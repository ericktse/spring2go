package com.spring2go.common.rule.engine.command;

import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;
import com.spring2go.common.rule.engine.entity.Student;

public class DemoRuleExpressionSubstitutionsRuleExecuteCommand implements RuleThenCommand {

    @Override
    public RuleResult execute(Rule rule, Object fact) {
        System.out.println("DemoRuleExpressionSubstitutionsRuleExecuteCommand execute,rule id:" + rule.getId());

        Student student = (Student) fact;
        student.setAge(8);
        RuleResult result = new RuleResult(rule);
        return result;
    }
}
