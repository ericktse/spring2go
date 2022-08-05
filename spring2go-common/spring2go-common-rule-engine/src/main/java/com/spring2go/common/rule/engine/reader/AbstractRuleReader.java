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

    public List<Rule> filterRules(List<Rule> rules, String parentId) {

        List<Rule> newItemList = new ArrayList<Rule>();

        for (int i = 0; i < rules.size(); i++) {
            Rule item = rules.get(i);
            if (StringUtils.isEmpty(parentId)) {
                if (!item.isSubRule()) {
                    newItemList.add(item);
                }
            } else {
                if (parentId.equalsIgnoreCase(item.getParent())) {
                    newItemList.add(item);
                }
            }
        }
        newItemList.sort(Comparator.comparing(p -> p.getPriority()));

        return newItemList;

    }

}
