package com.spring2go.auth.service;

import com.spring2go.common.core.constant.SecurityConstants;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.StringUtils;
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
public class InnerAuthorizeService {
    private final RemoteUserService remoteUserService;
    private final TokenUtils tokenUtils;

    public R login(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return R.failed("用户/密码必须填写");
        }
        // 查询用户信息
        R<SysUser> userResult = remoteUserService.getInfoByUserName(username, SecurityConstants.FROM_IN);
        if (userResult == null
                || R.FAIL == userResult.getCode()
                || StringUtils.isNull(userResult.getData())) {
            return R.failed("用户不存在");
        }

        //判断用户状态
        //...

        //获取用户权限
        R<Set<String>> roleResult = remoteUserService.getRoleByUserName(username, SecurityConstants.FROM_IN);
        if (roleResult == null
                || R.FAIL == roleResult.getCode()
                || StringUtils.isNull(roleResult.getData())) {
            return R.failed("角色不存在");
        }
        R<Set<String>> permsResult = remoteUserService.getPermsByUserName(username, SecurityConstants.FROM_IN);
        if (permsResult == null
                || R.FAIL == permsResult.getCode()
                || StringUtils.isNull(permsResult.getData())) {
            return R.failed("权限不存在");
        }

        //创建Token
        String token = tokenUtils.createToken(username, userResult.getData(), roleResult.getData(), permsResult.getData());

        //返回用户详情和token
        Map<String, Object> map = new HashMap<>();
        map.put("user", userResult.getData());
        map.put("token", token);
        return R.ok(map);
    }

    public void logout() {
        String token = TokenUtils.getTokenByRequest();
        if (StringUtils.isNotNull(token)) {
            // 删除用户缓存记录
            tokenUtils.deleteToken(token);
            // 记录用户退出日志
            //sysLoginService.logout(username);
        }
    }
}
