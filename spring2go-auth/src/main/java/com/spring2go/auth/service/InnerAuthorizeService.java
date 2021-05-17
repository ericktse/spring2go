package com.spring2go.auth.service;

import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.security.util.TokenUtils;
import com.spring2go.upms.api.entity.SysUser;
import com.spring2go.upms.api.feign.service.RemoteUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


/**
 * @description: 内部系统认证服务
 * @author: xiaobin
 * @date: 2021-05-12 12:11
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InnerAuthorizeService {
    private final RemoteUserService remoteUserService;
    private final TokenUtils tokenUtils;

    public R login(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return R.failed("用户/密码必须填写");
        }
        // 查询用户信息
        R<SysUser> userResult = remoteUserService.getInfoByUserName(username);
        if (userResult == null
                || R.FAIL == userResult.getCode()
                || StringUtils.isNull(userResult.getData())) {
            return R.failed("用户不存在");
        }

        //判断用户状态
        //...

        //获取用户权限
        R<Set<String>> roleResult = remoteUserService.getRoleByUserName(username);
        if (roleResult == null
                || R.FAIL == roleResult.getCode()
                || StringUtils.isNull(roleResult.getData())) {
            return R.failed("角色不存在");
        }
        R<Set<String>> permsResult = remoteUserService.getPermsByUserName(username);
        if (permsResult == null
                || R.FAIL == permsResult.getCode()
                || StringUtils.isNull(permsResult.getData())) {
            return R.failed("权限不存在");
        }

        //创建Token
        String token = tokenUtils.createToken(username, userResult.getData(), roleResult.getData(), permsResult.getData());
        log.info("------------测试 Redis:" + tokenUtils.getAuthorizationUser(token));

        return R.ok(token);
    }

    public void logout(SysUser loginUser) {
        if (StringUtils.isNotNull(loginUser)) {
            //String username = loginUser.getUsername();
            // 删除用户缓存记录
            //tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            //sysLoginService.logout(username);
        }
    }
}
