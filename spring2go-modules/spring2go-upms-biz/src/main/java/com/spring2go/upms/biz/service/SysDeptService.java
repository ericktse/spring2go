package com.spring2go.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.upms.api.dto.DeptTree;
import com.spring2go.upms.api.dto.SysDeptDTO;
import com.spring2go.upms.api.entity.SysDept;

import java.util.List;

/**
 * @description: 信息部门信息
 * @author: xiaobin
 * @date: 2021/3/31 17:09
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 查询部门数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    List<SysDept> selectDeptList(SysDeptDTO dept);

    /**
     * 查询部门数据-下拉树结构
     *
     * @param dept 部门列表
     * @return 下拉树结构列表
     */
    List<DeptTree> selectDeptTree(SysDeptDTO dept);

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    String checkDeptNameUnique(SysDept dept);

    /**
     * 是否存在部门子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public boolean hasChildByDeptId(Long deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkDeptExistUser(Long deptId);
}
