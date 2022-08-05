package com.spring2go.common.rule.engine.executor;

import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;
import com.spring2go.common.rule.engine.exception.RuleEngineException;

/**
 * 规则执行器
 *
 * @author xiaobin
 */
public abstract class AbstractRuleExecutor {

    public abstract Boolean check(Rule rule, Object object) throws RuleEngineException;

    public abstract RuleResult execute(Rule rule, Object object) throws RuleEngineException;

}
