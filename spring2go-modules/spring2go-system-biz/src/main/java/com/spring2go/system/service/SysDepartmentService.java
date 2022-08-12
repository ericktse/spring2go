package com.spring2go.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.system.vo.DepartmentTree;
import com.spring2go.system.vo.DepartmentVo;
import com.spring2go.system.entity.SysDepartment;

import java.util.List;

/**
 * @description: 部门信息
 * @author: xiaobin
 * @date: 2021/3/31 17:09
 */
public interface SysDepartmentService extends IService<SysDepartment> {

    /**
     * 查询部门数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    List<SysDepartment> selectDeptList(DepartmentVo dept);

    /**
     * 查询部门数据-下拉树结构
     *
     * @param dept 部门列表
     * @return 下拉树结构列表
     */
    List<DepartmentTree> selectDeptTree(DepartmentVo dept);

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    String checkDeptNameUnique(SysDepartment dept);

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
