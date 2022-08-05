package com.spring2go.common.rule.engine.command;

import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.exception.RuleEngineException;

/**
 * 规则检查命令
 *
 * @author xiaobin
 */
public interface RuleWhenCommand {
    Boolean check(Rule rule, Object fact) throws RuleEngineException;
}
