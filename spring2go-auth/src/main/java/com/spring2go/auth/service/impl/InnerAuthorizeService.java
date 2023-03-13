package com.spring2go.auth.service.impl;

import com.spring2go.auth.domain.LoginUser;
import com.spring2go.auth.service.AuthorizeService;
import com.spring2go.common.core.constant.CommonConstants;
import com.spring2go.common.core.constant.SecurityConstants;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.EncryptUtils;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.security.domain.SimpleAuthorizeUser;
import com.spring2go.common.security.util.TokenUtils;
import com.spring2go.system.entity.SysUser;
import com.spring2go.system.feign.service.RemoteUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @description: 内部系统认证服务
 * @author: xiaobin
 * @date: 2021-05-12 12:11
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InnerAuthorizeService implements AuthorizeService {
    private final RemoteUserService remoteUserService;
    private final TokenUtils tokenUtils;

    @Override
    public R login(String username, String password) {

        // 校验 用户名和密码
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return R.failed("用户/密码必须填写");
        }

        // 查询用户信息
        R<SysUser> userResult = remoteUserService.getInfoByUserName(username, SecurityConstants.FROM_IN);
        if (userResult == null
                || CommonConstants.FAIL == userResult.getCode()
                || StringUtils.isNull(userResult.getData())) {
            return R.failed("用户不存在");
        }

        //判断用户状态
        SysUser user = userResult.getData();
        if (!user.getDelFlag().equals("0")) {
            return R.failed("对不起，您的账号：" + username + " 已被删除");
        }

        if (!EncryptUtils.matchesPassword(password, user.getPassword())) {
            return R.failed("用户不存在/密码错误");
        }

        //获取用户权限
        R<Set<String>> roleResult = remoteUserService.getRoleByUserName(username, SecurityConstants.FROM_IN);
        if (roleResult == null
                || CommonConstants.FAIL == roleResult.getCode()) {
            return R.failed("获取角色异常");
        }
        R<Set<String>> permsResult = remoteUserService.getPermsByUserName(username, SecurityConstants.FROM_IN);
        if (permsResult == null
                || CommonConstants.FAIL == permsResult.getCode()) {
            return R.failed("获取权限异常");
        }

        //创建Token
        String token = tokenUtils.createToken(user.getUserId(), username, userResult.getData(), roleResult.getData(), permsResult.getData());

        //返回用户详情和token
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return R.ok(map);
    }

    @Override
    public void logout() {
        String token = TokenUtils.getTokenByRequest();
        if (StringUtils.isNotNull(token)) {
            // 删除用户缓存记录
            tokenUtils.deleteToken(token);
            // 记录用户退出日志
            //sysLoginService.logout(username);
        }
    }

    @Override
    public LoginUser getUserInfo() {
        String token = TokenUtils.getTokenByRequest();
        if (StringUtils.isNotNull(token)) {
            SimpleAuthorizeUser user = tokenUtils.getAuthorizeUser(token);

            LoginUser loginUser = new LoginUser();
            SysUser sysUser = (SysUser) user.getOriginalUser();
            loginUser.setUsername(user.getUsername());
            loginUser.setUserId(sysUser.getUserId());
            loginUser.setSysUser(sysUser);
            loginUser.setPermissions(user.getPermissions());
            loginUser.setRoles(user.getRoles());

            return loginUser;
        }

        return null;
    }

}
