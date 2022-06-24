package com.spring2go.system.controller;

import com.spring2go.common.core.controller.BaseController;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.log.annotation.Log;
import com.spring2go.common.security.util.SecurityUtils;
import com.spring2go.system.entity.SysMenu;
import com.spring2go.system.service.SysMenuService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


/**
 * @Description: 系统菜单
 * @author: xiaobin
 * @date: 2021-04-08 11:15
 */
@Api(tags="菜单管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {

    private final SysMenuService sysMenuService;

    /**
     * 返回当前用户的树形菜单集合
     *
     * @return 当前用户的树形菜单
     */
    @GetMapping
    public R getUserMenu() {
        Set<String> roles = SecurityUtils.getRoles();
        sysMenuService.selectMenuTreeByRoleIds(roles);
        return R.ok();
    }

    /**
     * 返回树形菜单集合
     *
     * @param parentId 父节点ID
     * @return 树形菜单
     */
    @GetMapping(value = "/tree")
    public R getTree(Integer parentId) {

        return R.ok(sysMenuService.selectMenuTree(parentId));
    }

    /**
     * 通过ID查询菜单的详细信息
     *
     * @param id 菜单ID
     * @return 菜单详细信息
     */
    @GetMapping("/{id}")
    public R getById(@PathVariable Integer id) {
        return R.ok(sysMenuService.getById(id));
    }

    /**
     * 新增菜单
     *
     * @param sysMenu 菜单信息
     * @return 含ID 菜单信息
     */
    @Log("新增菜单")
    @PostMapping
    public R save(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return R.ok(sysMenu);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return success/false
     */
    @Log("删除菜单")
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable Integer id) {
        return R.ok(sysMenuService.removeById(id));
    }

    /**
     * 更新菜单
     *
     * @param sysMenu
     * @return
     */
    @Log("更新菜单")
    @PutMapping
    public R update(@RequestBody SysMenu sysMenu) {
        return R.ok(sysMenuService.updateById(sysMenu));
    }

}

