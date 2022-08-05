package com.spring2go.common.rule.engine;

import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;
import com.spring2go.common.rule.engine.exception.RuleEngineException;
import com.spring2go.common.rule.engine.executor.AbstractRuleExecutor;
import com.spring2go.common.rule.engine.executor.ComplexRuleExecutor;
import com.spring2go.common.rule.engine.reader.AbstractRuleReader;
import com.spring2go.common.rule.engine.reader.XmlRuleReader;

import java.util.List;

/**
 * 规则引擎
 *
 * @author xiaobin
 */
public class RuleEngineService {
    private AbstractRuleReader ruleReader;
    private AbstractRuleExecutor ruleExecutor;

    public RuleResult run(Object object) throws RuleEngineException {
        ruleReader = new XmlRuleReader();
        List<Rule> itemList = ruleReader.loadRules();
        itemList = ruleReader.filterRules(itemList, null);

        ruleExecutor = new ComplexRuleExecutor();
        RuleResult result = new RuleResult(null);
        for (Rule item : itemList) {
            if (ruleExecutor.check(item, object)) {
                RuleResult itemResult = ruleExecutor.execute(item, object);
                if (null != itemResult) {
                    result.addItemResult(itemResult);
                }
            }
        }

        return result;
    }
}
