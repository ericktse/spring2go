package com.spring2go.common.rule.engine;

import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.entity.RuleResult;
import com.spring2go.common.rule.engine.exception.RuleEngineException;
import com.spring2go.common.rule.engine.executor.RuleExecutor;
import com.spring2go.common.rule.engine.executor.ComplexRuleExecutor;
import com.spring2go.common.rule.engine.reader.AbstractRuleReader;
import com.spring2go.common.rule.engine.reader.XmlRuleReader;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 规则引擎
 *
 * @author xiaobin
 */
@AllArgsConstructor
public class RuleEngineService {

    private final AbstractRuleReader ruleReader;

    public RuleResult run(Object fact, String ruleId) throws RuleEngineException {
        List<Rule> itemList = ruleReader.loadRules();
        itemList = ruleReader.filterRules(itemList, ruleId);

        RuleExecutor ruleExecutor = new ComplexRuleExecutor();
        RuleResult result = new RuleResult(null);
        for (Rule item : itemList) {
            if (ruleExecutor.check(item, fact)) {
                RuleResult itemResult = ruleExecutor.execute(item, fact);
                if (null != itemResult) {
                    result.addItemResult(itemResult);
                }
            }
        }

        return result;
    }

    public RuleResult run(Object fact) throws RuleEngineException {
        return run(fact, null);
    }
}
