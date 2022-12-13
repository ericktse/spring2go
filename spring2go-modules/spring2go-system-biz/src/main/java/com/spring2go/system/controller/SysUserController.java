package com.spring2go.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.security.annotation.Inner;
import com.spring2go.common.security.util.SecurityUtils;
import com.spring2go.system.vo.UserInfo;
import com.spring2go.system.vo.UserVo;
import com.spring2go.system.entity.SysUser;
import com.spring2go.system.service.SysUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Description: 系统用户
 * @author: xiaobin
 * @date: 2021-03-30 10:25
 */
@Api(tags = "用户管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class SysUserController {
    private final SysUserService userService;

    /**
     * 获取当前用户全部信息
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/info"})
    public R info() {
        Long userId = SecurityUtils.getUserId();
        UserInfo user = userService.getUserInfoById(userId);
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
    public R detail(@PathVariable Long id) {
        UserInfo user = userService.getUserInfoById(id);
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
    @PostMapping("/add")
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
     * 重置密码
     */
    @PutMapping("/resetPwd")
    public R resetPwd(@RequestBody UserVo userVo) {
        if(userVo.getUserId()== null){
            userVo.setUserId(SecurityUtils.getUserId());
        }

        return R.ok(userService.resetPwd(userVo));
    }

    /**
     * 分页查询用户
     *
     * @param page   参数集
     * @param userVo 查询参数列表
     * @return 用户集合
     */
    @GetMapping("/page")
    public R getPage(Page page, UserVo userVo) {
        return R.ok(userService.getUserWithRolePage(page, userVo));
    }

    /**
     * 查询已分配用户角色列表
     */
    @GetMapping("/authUser/allocatedList")
    public R allocatedList(UserVo userVo)
    {
        return R.ok(userService.selectAllocatedList(userVo));
    }

    /**
     * 查询未分配用户角色列表
     */
    @GetMapping("/authUser/unallocatedList")
    public R unallocatedList(UserVo userVo)
    {
        return R.ok(userService.selectUnallocatedList(userVo));
    }
}
