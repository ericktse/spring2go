package com.spring2go.system.dto;

import com.spring2go.common.core.domain.TreeNode;
import lombok.Data;


/**
 * @Description: 部门树结构
 * @author: xiaobin
 * @date: 2021-04-01 13:56
 */
@Data
public class DeptTree extends TreeNode {

    private String name;
}
