package com.spring2go.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.common.core.controller.BaseController;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.DateUtils;
import com.spring2go.common.security.annotation.Inner;
import com.spring2go.system.service.SysUserRoleService;
import com.spring2go.system.service.SysUserService;
import com.spring2go.system.vo.RoleVo;
import com.spring2go.system.entity.SysRole;
import com.spring2go.system.service.SysRoleMenuService;
import com.spring2go.system.service.SysRoleService;
import com.spring2go.system.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: 系统角色
 * @author: xiaobin
 * @date: 2021-04-08 11:15
 */
@Api(tags = "角色管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class SysRoleController extends BaseController {

    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;
    private final SysUserRoleService sysUserRoleService;
    private final SysUserService userService;

    @Inner
    @ApiOperation("通过用户名获取角色信息")
    @GetMapping("/getRoleByUserName")
    public R getRoleByUserName(@RequestParam String username) {
        List<SysRole> sysRoles = sysRoleService.getRoleByUserName(username);
        Set<String> roles = sysRoles.stream().map(SysRole::getRoleName).collect(Collectors.toSet());
        return R.ok(roles);
    }

    @Inner
    @ApiOperation("通过用户名获取权限信息")
    @GetMapping("/getPermsByUserName")
    public R getPermsByUserName(@RequestParam String username) {
        return R.ok(sysRoleService.getPermsByUserName(username));
    }

    /**
     * 通过ID查询角色信息
     *
     * @param id ID
     * @return 角色信息
     */
    @GetMapping("/{id}")
    public R getById(@PathVariable Integer id) {
        return R.ok(sysRoleService.getById(id));
    }

    /**
     * 添加角色
     *
     * @param sysRole 角色信息
     * @return success、false
     */
    @PostMapping("/add")
    public R save(@Valid @RequestBody SysRole sysRole) {
        sysRole.setCreateTime(DateUtils.now());
        return R.ok(sysRoleService.save(sysRole));
    }

    /**
     * 修改角色
     *
     * @param sysRole 角色信息
     * @return success/false
     */
    @PutMapping("/edit")
    public R update(@Valid @RequestBody SysRole sysRole) {
        return R.ok(sysRoleService.updateById(sysRole));
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable Long id) {
        return R.ok(sysRoleService.removeById(id));
    }

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    @GetMapping("/list")
    public R listRoles() {
        return R.ok(sysRoleService.list(Wrappers.emptyWrapper()));
    }

    /**
     * 分页查询角色信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R getRolePage(Page page, RoleVo roleVo) {
        return R.ok(sysRoleService.getPage(page, roleVo));
    }

    /**
     * 更新角色菜单
     *
     * @param roleVo 角色对象
     * @return success、false
     */
    @PutMapping("/menu")
    public R saveRoleMenus(@RequestBody RoleVo roleVo) {
        return R.ok(sysRoleMenuService.saveRoleMenus(roleVo.getRoleId(), roleVo.getMenuIds()));
    }

    /**
     * 修改保存数据权限
     */
    @PutMapping("/dataScope")
    public R dataScope(@RequestBody RoleVo roleVo) {
        return R.ok(sysRoleService.authDataScope(roleVo));
    }


    /**
     * 查询已分配用户角色列表
     */
    @GetMapping("/authUser/allocatedList")
    public R allocatedList(Page page, UserVo userVo) {
        return R.ok(sysUserRoleService.getAllocatedPage(page, userVo));
    }

    /**
     * 查询未分配用户角色列表
     */
    @GetMapping("/authUser/unallocatedList")
    public R unallocatedList(Page page, UserVo userVo) {
        return R.ok(sysUserRoleService.getUnallocatedPage(page, userVo));
    }

    /**
     * 取消授权用户
     */
    @PutMapping("/authUser/cancel")
    public R cancelAuthUser(@RequestBody RoleVo roleVo) {
        return R.ok(sysUserRoleService.deleteAuthUser(roleVo.getRoleId(), roleVo.getUserId()));
    }

    /**
     * 批量取消授权用户
     */
    @PutMapping("/authUser/cancelAll")
    public R cancelAuthUserAll(Long roleId, Long[] userIds) {
        return R.ok(sysUserRoleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 批量选择用户授权
     */
    @PutMapping("/authUser/selectAll")
    public R selectAuthUserAll(Long roleId, Long[] userIds) {
        return R.ok(sysUserRoleService.insertAuthUsers(roleId, userIds));
    }
}
