package com.spring2go.upms.biz.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.security.annotation.Inner;
import com.spring2go.common.security.util.SecurityUtils;
import com.spring2go.upms.api.dto.UserDTO;
import com.spring2go.upms.api.entity.SysUser;
import com.spring2go.upms.biz.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    /**
     * 获取当前用户全部信息
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/info"})
    public R info() {
        String username = SecurityUtils.getUsername();
        SysUser user = userService.getUserInfoByName(username);
        if (user == null) {
            return R.failed("获取当前用户信息失败");
        }
        return R.ok(user);
    }

    /**
     * 通过username查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Inner
    @GetMapping("/info/{username}")
    public R info(@PathVariable String username) {
        if (StringUtils.isEmpty(username)) {
            return R.failed("用户名称不能为空");
        }

        SysUser user = userService.getUserInfoByName(username);
        if (user == null) {
            return R.failed(String.format("用户信息为空 %s", username));
        }
        return R.ok(user);
    }

    /**
     * 通过ID获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/{id}"})
    public R detail(@PathVariable Integer id) {
        SysUser user = userService.getUserInfoById(id);
        if (user == null) {
            return R.failed("获取用户信息失败");
        }
        return R.ok(user);
    }


    /**
     * 删除用户信息
     *
     * @param id ID
     * @return R
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        return R.ok(userService.removeById(id));
    }

    /**
     * 添加用户
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @PostMapping
    public R user(@RequestBody UserDTO userDto) {
        return R.ok(userService.saveUser(userDto));
    }

    /**
     * 更新用户信息
     *
     * @param userDto 用户信息
     * @return R
     */
    @PutMapping("/edit")
    public R updateUser(@Valid @RequestBody UserDTO userDto) {
        return R.ok(userService.updateUser(userDto));
    }

    /**
     * 分页查询用户
     *
     * @param page    参数集
     * @param userDTO 查询参数列表
     * @return 用户集合
     */
    @GetMapping("/page")
    public R getUserPage(Page page, UserDTO userDTO) {
        return R.ok(userService.getUserWithRolePage(page, userDTO));
    }
}
