package com.spring2go.upms.biz.controller;

import com.spring2go.common.core.constant.CommonConstants;
import com.spring2go.common.core.controller.BaseController;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.log.annotation.Log;
import com.spring2go.common.security.annotation.Inner;
import com.spring2go.upms.api.dto.DeptDTO;
import com.spring2go.upms.api.entity.SysDept;
import com.spring2go.upms.biz.service.SysDeptService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 系统部门信息
 * @author: xiaobin
 * @date: 2021-03-31 16:58
 */
@Api("系统部门管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends BaseController {

    private final SysDeptService sysDeptService;

    /**
     * 根据部门编号获取详细信息
     */
    @Inner
    @GetMapping(value = "/{deptId}")
    public R getById(@PathVariable Long deptId) {
        return R.ok(sysDeptService.getById(deptId));
    }

    /**
     * 获取部门列表
     */
    @Log("获取部门列表")
    @GetMapping("/list")
    public R list(DeptDTO dept) {
        List<SysDept> list = sysDeptService.selectDeptList(dept);
        return R.ok(list);
    }

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/tree")
    @RequiresPermissions({"system:dept:tree"})
    public R tree(DeptDTO dept) {
        return R.ok(sysDeptService.selectDeptTree(dept));
    }

    /**
     * 新增部门
     */
    @PostMapping
    public R save(@Validated @RequestBody SysDept dept) {
        if (CommonConstants.NOT_UNIQUE.equals(sysDeptService.checkDeptNameUnique(dept))) {
            return R.failed("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        //dept.setCreateBy(SecurityUtils.getUsername());
        return R.ok(sysDeptService.save(dept));
    }

    /**
     * 修改部门
     */
    @PutMapping
    public R update(@Validated @RequestBody SysDept dept) {
        if (CommonConstants.NOT_UNIQUE.equals(sysDeptService.checkDeptNameUnique(dept))) {
            return R.failed("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (dept.getParentId().equals(dept.getDeptId())) {
            return R.failed("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }

        //dept.setUpdateBy(SecurityUtils.getUsername());

        return R.ok(sysDeptService.updateById(dept));
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{deptId}")
    public R removeById(@PathVariable Long deptId) {
        if (sysDeptService.hasChildByDeptId(deptId)) {
            return R.failed("存在下级部门,不允许删除");
        }
        if (sysDeptService.checkDeptExistUser(deptId)) {
            return R.failed("部门存在用户,不允许删除");
        }
        return R.ok(sysDeptService.removeById(deptId));
    }
}
