package com.spring2go.common.rule.engine.util;

import com.spring2go.common.rule.engine.entity.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则缓存
 *
 * @author xiaobin
 */
public class RuleCacheUtils {
    public static volatile List<Rule> RULE_CACHE = new ArrayList<Rule>();


    public static Rule getRule(String id) {

        for (Rule rule : RULE_CACHE) {
            if (rule.getId().equalsIgnoreCase(id)) {
                return rule;
            }
        }

        return null;
    }
}
