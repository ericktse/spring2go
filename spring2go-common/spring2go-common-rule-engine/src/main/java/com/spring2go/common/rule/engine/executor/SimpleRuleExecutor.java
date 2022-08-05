package com.spring2go.common.rule.engine.executor;

import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.rule.engine.command.RuleWhenCommand;
import com.spring2go.common.rule.engine.command.RuleThenCommand;
import com.spring2go.common.rule.engine.command.SqlRuleWhenCommand;
import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;
import com.spring2go.common.rule.engine.exception.RuleEngineException;
import lombok.extern.slf4j.Slf4j;

/**
 * 复杂规则执行器
 *
 * @author xiaobin
 */
@Slf4j
public class SimpleRuleExecutor extends AbstractRuleExecutor {
    @Override
    public Boolean check(Rule rule, Object object) throws RuleEngineException {

        if (StringUtils.isNotEmpty(rule.getWhenSql())) {
            SqlRuleWhenCommand command = new SqlRuleWhenCommand();
            return command.check(rule, object);
        }

        if (StringUtils.isNotEmpty(rule.getWhenClass())) {
            RuleWhenCommand command = getRuleCommand(rule.getWhenClass());
            if (null != command) {
                return command.check(rule, object);
            }
            return false;
        }

        return false;
    }

    @Override
    public RuleResult execute(Rule rule, Object object) throws RuleEngineException {

        if (StringUtils.isNotEmpty(rule.getThenSql())) {

        }

        if (StringUtils.isNotEmpty(rule.getThenClass())) {
            RuleThenCommand command = getRuleCommand(rule.getThenClass());
            if (null != command) {
                return command.execute(rule, object);
            }
            return null;
        }

        return null;
    }

    private <T> T getRuleCommand(String clazz) throws RuleEngineException {

        Class<T> commandClass;
        try {
            if (StringUtils.isNotEmpty(clazz)) {
                commandClass = (Class<T>) Class.forName(clazz);
                T command = commandClass.newInstance();
                return command;
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new RuleEngineException(e);
        }

        return null;
    }
}
