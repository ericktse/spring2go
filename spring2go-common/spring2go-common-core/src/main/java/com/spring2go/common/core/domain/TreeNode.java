package com.spring2go.common.core.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 树结构
 * @author: xiaobin
 * @date: 2021-04-01 14:07
 */
@Data
public class TreeNode {
    protected Long id;

    protected Long parentId;

    protected List<TreeNode> children = new ArrayList<TreeNode>();

    protected Boolean hasChildren;

    public void add(TreeNode node) {
        children.add(node);
    }
}
