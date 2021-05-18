package com.spring2go.upms.biz.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.spring2go.common.core.controller.BaseController;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.security.annotation.Inner;
import com.spring2go.upms.api.entity.SysUser;
import com.spring2go.upms.biz.service.SysRoleService;
import com.spring2go.upms.biz.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

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

    private final SysRoleService roleService;

    @Inner
    @ApiOperation("通过用户名获取角色信息")
    @GetMapping("/getRoleByUserName")
    public R getRoleByUserName(@RequestParam String username) {

        //临时：
        Set<String> roles = new HashSet();
        roles.add("admin");
        roles.add("user1");

        return R.ok(roles);
    }

    @Inner
    @ApiOperation("通过用户名获取权限信息")
    @GetMapping("/getPermsByUserName")
    public R getPermsByUserName(@RequestParam String username) {

        //临时：
        Set<String> roles = new HashSet();
        roles.add("system:user:list");
        roles.add("system:role:list");
        roles.add("system:dept:tree");

        return R.ok(roles);
    }
}
