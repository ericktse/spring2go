package com.spring2go.system.controller;

import com.spring2go.common.core.constant.CommonConstants;
import com.spring2go.common.core.controller.BaseController;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.DateUtils;
import com.spring2go.common.log.annotation.Log;
import com.spring2go.common.security.annotation.Inner;
import com.spring2go.common.security.util.SecurityUtils;
import com.spring2go.system.vo.DepartmentVo;
import com.spring2go.system.entity.SysDepartment;
import com.spring2go.system.service.SysDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 系统部门信息
 * @author: xiaobin
 * @date: 2021-03-31 16:58
 */
@Api(tags = "部门管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/dept")
public class SysDepartmentController extends BaseController {

    private final SysDepartmentService sysDepartmentService;

    /**
     * 根据部门ID获取详情
     */
    @ApiOperation("根据部门ID获取详情")
    @Inner
    @GetMapping(value = "/{id}")
    public R getById(@PathVariable Long id) {
        return R.ok(sysDepartmentService.getById(id));
    }

    /**
     * 获取部门列表
     */
    @Log("获取部门列表")
    @GetMapping("/list")
    public R list(DepartmentVo dept) {
        List<SysDepartment> list = sysDepartmentService.selectDeptList(dept);
        return R.ok(list);
    }

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/tree")
    @RequiresPermissions({"system:dept:tree"})
    public R tree(DepartmentVo dept) {
        return R.ok(sysDepartmentService.selectDeptTree(dept));
    }

    /**
     * 新增部门
     */
    @PostMapping
    public R save(@Validated @RequestBody SysDepartment dept) {
        if (CommonConstants.NOT_UNIQUE.equals(sysDepartmentService.checkDeptNameUnique(dept))) {
            return R.failed("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateBy(SecurityUtils.getUsername());
        dept.setCreateTime(DateUtils.now());
        return R.ok(sysDepartmentService.save(dept));
    }

    /**
     * 修改部门
     */
    @PutMapping
    public R update(@Validated @RequestBody SysDepartment dept) {
        if (CommonConstants.NOT_UNIQUE.equals(sysDepartmentService.checkDeptNameUnique(dept))) {
            return R.failed("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (dept.getParentId().equals(dept.getDeptId())) {
            return R.failed("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }

        dept.setUpdateBy(SecurityUtils.getUsername());
        dept.setUpdateTime(DateUtils.now());

        return R.ok(sysDepartmentService.updateById(dept));
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable Long id) {
        if (sysDepartmentService.hasChildByDeptId(id)) {
            return R.failed("存在下级部门,不允许删除");
        }
        if (sysDepartmentService.checkDeptExistUser(id)) {
            return R.failed("部门存在用户,不允许删除");
        }
        return R.ok(sysDepartmentService.removeById(id));
    }

    /**
     * 加载对应角色部门列表树
     */
    @GetMapping(value = "/roleDeptTreeselect/{roleId}")
    public R roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("checkedKeys", sysDepartmentService.selectDeptListByRoleId(roleId));
        data.put("depts", sysDepartmentService.selectDeptTree(new DepartmentVo()));
        return R.ok(data);
    }
}
