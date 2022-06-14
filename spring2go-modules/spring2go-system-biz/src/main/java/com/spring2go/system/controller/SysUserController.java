package com.spring2go.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.security.annotation.Inner;
import com.spring2go.common.security.util.SecurityUtils;
import com.spring2go.system.vo.UserVo;
import com.spring2go.system.entity.SysUser;
import com.spring2go.system.service.SysUserService;
import io.swagger.annotations.Api;
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
     * @param userVo 用户信息
     * @return success/false
     */
    @PostMapping
    public R user(@RequestBody UserVo userVo) {
        return R.ok(userService.saveUser(userVo));
    }

    /**
     * 更新用户信息
     *
     * @param userVo 用户信息
     * @return R
     */
    @PutMapping("/edit")
    public R updateUser(@Valid @RequestBody UserVo userVo) {
        return R.ok(userService.updateUser(userVo));
    }

    /**
     * 分页查询用户
     *
     * @param page   参数集
     * @param userVo 查询参数列表
     * @return 用户集合
     */
    @GetMapping("/page")
    public R getUserPage(Page page, UserVo userVo) {
        return R.ok(userService.getUserWithRolePage(page, userVo));
    }
}
