package com.spring2go.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.common.core.constant.CommonConstants;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.core.util.TreeUtils;
import com.spring2go.upms.api.dto.DeptTree;
import com.spring2go.upms.api.entity.SysDept;
import com.spring2go.upms.api.entity.SysUser;
import com.spring2go.upms.biz.mapper.SysDeptMapper;
import com.spring2go.upms.biz.service.SysDeptService;
import com.spring2go.upms.biz.service.SysUserService;
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
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysUserService sysUserService;

    /**
     * 查询部门数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    public List<SysDept> selectDeptList(SysDept dept) {

        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        if (dept.getParentId() != null && dept.getParentId() != 0) {
            queryWrapper.eq("parent_id", dept.getParentId());
        }
        if (StringUtils.isNotEmpty(dept.getDeptName())) {
            queryWrapper.like("dept_name", dept.getDeptName());
        }
        if (StringUtils.isNotEmpty(dept.getStatus())) {
            queryWrapper.eq("status", dept.getStatus());
        }
        queryWrapper.orderByAsc("parent_id", "order_num");

        List<SysDept> list = this.list(queryWrapper);
        return list;
    }

    /**
     * 构建部门列表-树结构
     *
     * @param dept 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<DeptTree> selectDeptTree(SysDept dept) {
        List<SysDept> list = selectDeptList(dept);

        List<DeptTree> treeList = list.stream().filter(item -> !item.getDeptId().equals(item.getParentId()))
                .sorted(Comparator.comparingInt(SysDept::getOrderNum)).map(item -> {
                    DeptTree node = new DeptTree();
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
    public String checkDeptNameUnique(SysDept dept) {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("dept_name", dept.getDeptName())
                .eq("parent_id", dept.getParentId());

        SysDept info = this.getOne(queryWrapper);

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
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
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
}
