package com.spring2go.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.Master;
import com.baomidou.dynamic.datasource.annotation.Slave;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.common.core.constant.CommonConstants;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.core.util.TreeUtils;
import com.spring2go.system.entity.SysRole;
import com.spring2go.system.mapper.SysDepartmentMapper;
import com.spring2go.system.service.SysRoleService;
import com.spring2go.system.vo.DepartmentTree;
import com.spring2go.system.vo.DepartmentVo;
import com.spring2go.system.entity.SysDepartment;
import com.spring2go.system.entity.SysUser;
import com.spring2go.system.service.SysDepartmentService;
import com.spring2go.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 系统部门信息
 * @author: xiaobin
 * @date: 2021-03-31 17:10
 */
@Service
@RequiredArgsConstructor
public class SysDepartmentServiceImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements SysDepartmentService {

    private final SysUserService sysUserService;
    private final SysRoleService roleService;

    /**
     * 查询部门数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Master
    @Override
    public List<SysDepartment> selectDeptList(DepartmentVo dept) {

        LambdaQueryWrapper<SysDepartment> queryWrapper = Wrappers.lambdaQuery();
        if (dept.getParentId() != null && dept.getParentId() != 0) {
            queryWrapper.eq(SysDepartment::getParentId, dept.getParentId());
        }
        if (StringUtils.isNotEmpty(dept.getDeptName())) {
            queryWrapper.like(SysDepartment::getDeptName, dept.getDeptName());
        }
        if (StringUtils.isNotEmpty(dept.getStatus())) {
            queryWrapper.eq(SysDepartment::getStatus, dept.getStatus());
        }
        queryWrapper.eq(SysDepartment::getDelFlag, "0");
        queryWrapper.orderByAsc(SysDepartment::getParentId, SysDepartment::getOrderNum);

        List<SysDepartment> list = this.list(queryWrapper);
        return list;
    }

    /**
     * 构建部门列表-树结构
     *
     * @param dept 部门列表
     * @return 下拉树结构列表
     */
    @Slave
    @Override
    public List<DepartmentTree> selectDeptTree(DepartmentVo dept) {
        List<SysDepartment> list = selectDeptList(dept);

        List<DepartmentTree> treeList = list.stream().filter(item -> !item.getDeptId().equals(item.getParentId()))
                .sorted(Comparator.comparingInt(SysDepartment::getOrderNum)).map(item -> {
                    DepartmentTree node = new DepartmentTree();
                    node.setId(item.getDeptId());
                    node.setParentId(item.getParentId());
                    node.setName(item.getDeptName());
                    return node;
                }).collect(Collectors.toList());
        return TreeUtils.build(treeList);
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDepartment dept) {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        QueryWrapper<SysDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("dept_name", dept.getDeptName())
                .eq("parent_id", dept.getParentId());

        SysDepartment info = this.getOne(queryWrapper);

        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
            return CommonConstants.NOT_UNIQUE;
        }
        return CommonConstants.UNIQUE;
    }

    /**
     * 是否存在部门子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(Long deptId) {
        QueryWrapper<SysDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("del_flag", "0")
                .eq("parent_id", deptId);

        int result = this.count(queryWrapper);
        return result > 0 ? true : false;
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("del_flag", "0")
                .eq("dept_id", deptId);

        int result = sysUserService.count(queryWrapper);

        return result > 0 ? true : false;
    }

    @Override
    public List<Long> selectDeptListByRoleId(Long roleId) {
        SysRole role = roleService.getById(roleId);
        return baseMapper.selectDeptListByRoleId(roleId, role.getDeptCheckStrictly());
    }
}
