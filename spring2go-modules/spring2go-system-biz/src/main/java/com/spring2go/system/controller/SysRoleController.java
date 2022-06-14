package com.spring2go.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.common.core.controller.BaseController;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.security.annotation.Inner;
import com.spring2go.system.dto.RoleDTO;
import com.spring2go.system.entity.SysRole;
import com.spring2go.system.service.SysRoleMenuService;
import com.spring2go.system.service.SysRoleService;
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
@Api("菜单角色模块")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {

    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;

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
    @PostMapping
    public R save(@Valid @RequestBody SysRole sysRole) {
        return R.ok(sysRoleService.save(sysRole));
    }

    /**
     * 修改角色
     *
     * @param sysRole 角色信息
     * @return success/false
     */
    @PutMapping
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
    public R removeById(@PathVariable Integer id) {
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
    public R getRolePage(Page page) {
        return R.ok(sysRoleService.page(page, Wrappers.emptyWrapper()));
    }

    /**
     * 更新角色菜单
     *
     * @param roleDto 角色对象
     * @return success、false
     */
    @PutMapping("/menu")
    public R saveRoleMenus(@RequestBody RoleDTO roleDto) {
        return R.ok(sysRoleMenuService.saveRoleMenus(roleDto.getRoleId(), roleDto.getMenuIds()));
    }
}