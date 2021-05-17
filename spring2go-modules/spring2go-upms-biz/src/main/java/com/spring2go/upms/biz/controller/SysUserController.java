package com.spring2go.upms.biz.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.spring2go.common.core.domain.R;
import com.spring2go.upms.api.entity.SysUser;
import com.spring2go.upms.biz.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 系统用户
 * @author: xiaobin
 * @date: 2021-03-30 10:25
 */
@Api("系统用户管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/user")
public class SysUserController {
    private final SysUserService userService;

    @ApiOperation("通过用户名获取用户信息")
    @GetMapping("/getInfoByUserName")
    public R getInfoByUserName(@RequestParam String username) {
        username = "admin";
        SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUserName, username));
        if (user == null) {
            return R.failed("获取当前用户信息失败");
        }
        return R.ok(user);
    }

    /**
     * 获取当前用户全部信息
     *
     * @return 用户信息
     */
    @GetMapping("/info/{userName}")
    public R info(@PathVariable String userName) {
        String username = "admin";
        SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUserName, username));
        if (user == null) {
            return R.failed("获取当前用户信息失败");
        }
        return R.ok(user);
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public R user(@PathVariable Integer id) {
        return R.ok(userService.getUserInfoById(id));
    }
}
