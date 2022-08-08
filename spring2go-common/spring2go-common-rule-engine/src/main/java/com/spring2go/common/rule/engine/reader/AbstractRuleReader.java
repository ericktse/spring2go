package com.spring2go.common.rule.engine.reader;

import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.exception.RuleEngineException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 规则配置解析
 *
 * @author xiaobin
 */
public abstract class AbstractRuleReader {

    public abstract List<Rule> loadRules() throws RuleEngineException;

    public List<Rule> filterRules(List<Rule> rules, String ruleId) {

        List<Rule> newItemList = new ArrayList<Rule>();

        for (int i = 0; i < rules.size(); i++) {
            Rule item = rules.get(i);
            if (StringUtils.isEmpty(ruleId)) {
                if (!item.isSubRule() && item.getEnabled()) {
                    newItemList.add(item);
                }
            } else {
                if (ruleId.equalsIgnoreCase(item.getId()) && item.getEnabled()) {
                    newItemList.add(item);
                }
            }
        }
        newItemList.sort(Comparator.comparing(p -> p.getPriority()));

        return newItemList;

    }

}
