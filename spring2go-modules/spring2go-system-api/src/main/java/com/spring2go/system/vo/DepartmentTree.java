package com.spring2go.system.vo;

import com.spring2go.common.core.domain.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @Description: 部门树结构
 * @author: xiaobin
 * @date: 2021-04-01 13:56
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class DepartmentTree extends TreeNode {

    private String name;
}
