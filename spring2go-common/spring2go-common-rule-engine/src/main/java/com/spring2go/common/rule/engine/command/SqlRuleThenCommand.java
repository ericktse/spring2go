package com.spring2go.common.rule.engine.command;

import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;

/**
 * SQL执行命令
 *
 * @author xiaobin
 */
public class SqlRuleThenCommand implements RuleThenCommand {

    @Override
    public RuleResult execute(Rule rule, Object fact) {
        return null;
    }
}
