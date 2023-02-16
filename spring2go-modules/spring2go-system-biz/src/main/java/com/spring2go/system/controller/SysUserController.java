package com.spring2go.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.core.util.excel.ExcelUtils;
import com.spring2go.common.log.annotation.Log;
import com.spring2go.common.security.annotation.Inner;
import com.spring2go.common.security.util.SecurityUtils;
import com.spring2go.system.entity.SysRole;
import com.spring2go.system.service.SysRoleService;
import com.spring2go.system.service.SysUserRoleService;
import com.spring2go.system.vo.UserInfo;
import com.spring2go.system.vo.UserVo;
import com.spring2go.system.entity.SysUser;
import com.spring2go.system.service.SysUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final SysRoleService sysRoleService;
    private final SysUserRoleService sysUserRoleService;

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
        if (userVo.getUserId() == null) {
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
     * 根据用户编号获取授权角色
     */
    @GetMapping("/authRole/{userId}")
    public R authRole(@PathVariable("userId") Long userId) {
        SysUser user = userService.getById(userId);
        List<SysRole> roles = sysRoleService.getRoleByUserName(user.getUserName());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("user", user);
        data.put("roles", roles);
        return R.ok(data);
    }

    /**
     * 用户授权角色
     */
    @PutMapping("/authRole")
    public R insertAuthRole(Long userId, Long[] roleIds) {
        sysUserRoleService.insertAuthUsers(userId, roleIds);
        return R.ok();
    }

    @Log("用户数据导出")
    @PostMapping("/exportExcel")
    public void export(HttpServletResponse response, SysUser user) {
        List<SysUser> list = userService.list(Wrappers.emptyWrapper());
        ExcelUtils<SysUser> util = new ExcelUtils<SysUser>(SysUser.class);
        util.exportExcel(response, list);
    }

    @Log("用户数据导入")
    @PostMapping("/importExcel")
    public R importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtils<SysUser> util = new ExcelUtils<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        //String message = userService.importUser(userList, updateSupport, operName);
        return R.ok("导入成功");
    }

    @PostMapping("/exportExcelTemplate")
    public void importTemplate(HttpServletResponse response) throws Exception {
        ExcelUtils<SysUser> util = new ExcelUtils<SysUser>(SysUser.class);
        util.exportExcelTemplate(response, "用户数据");
    }
}
