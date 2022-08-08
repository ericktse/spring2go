package com.spring2go.common.rule.engine.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则执行结果
 *
 * @author xiaobin
 */
@Data
public class RuleResult {
    private List<RuleResult> itemResults;
    private Rule rule;
    private RuleResultStatus status;

    public RuleResult(Rule rule) {
        this.itemResults = new ArrayList<>();
        this.status = RuleResultStatus.DEFAULT;
        this.rule = rule;
    }

    public void addItemResult(RuleResult itemResult) {
        itemResults.add(itemResult);
    }
}
