package com.spring2go.auth.service;

import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.upms.api.entity.SysUser;
import com.spring2go.upms.api.feign.service.RemoteUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


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

    public R login(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return R.failed("用户/密码必须填写");
        }
        // 查询用户信息
        R<SysUser> userResult = remoteUserService.getUserInfo(username);
        if (userResult == null
                || R.FAIL == userResult.getCode()
                || StringUtils.isNull(userResult.getData())) {
            return R.failed("用户不存在");
        }

        //其他用户状态判断

        //创建Token

        return R.ok(userResult.getData());
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
