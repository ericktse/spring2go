package com.spring2go.common.rule.engine.command;

import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;

/**
 * 规则执行命令
 *
 * @author xiaobin
 */
public interface RuleThenCommand {

    RuleResult execute(Rule rule, Object fact);
}
