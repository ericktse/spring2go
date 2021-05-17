package com.spring2go.common.security.shiro;

import com.spring2go.common.security.domain.SimpleAuthorizeUser;
import com.spring2go.common.security.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @description: 自定义权限Realm
 * @author: xiaobin
 * @date: 2021-05-13 14:30
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ShiroAuthorizeRealm extends AuthorizingRealm {

    private final TokenUtils tokenUtils;


    /**
     * @description: 授权校验
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("===============Shiro权限认证开始==============");

        SimpleAuthorizeUser user = (SimpleAuthorizeUser) getAvailablePrincipal(principalCollection);
        Set<String> roles = user.getRoles();
        Set<String> perms = user.getPermissions();

        // 将用户用户角色和权限赋值Shiro对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(perms);
        return info;
    }

    /**
     * @description: 认证校验
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        if (token == null) {
            throw new UnauthorizedException("token非法无效");
        }

        Set<String> roles = new HashSet();
        roles.add("admin");
        roles.add("user1");
        Set<String> perms = new HashSet();
        perms.add("system:user:list");
        perms.add("system:role:list");

        SimpleAuthorizeUser user = new SimpleAuthorizeUser("user1", null, roles, perms);
//        // 校验token有效性
//        SimpleAuthorizationUser user = tokenUtils.getAuthorizationUser(token);
//        if (user == null) {
//            throw new UnauthorizedException("token已过期");
//        }
//
//        // 刷新token,（实现： 用户在线操作不掉线功能）
//        tokenUtils.refreshToken(token);

        //查询用户的角色和权限存到SimpleAuthenticationInfo中，这样在其它地方
        //SecurityUtils.getSubject().getPrincipal()就能拿出用户的所有信息，包括角色和权限
        return new SimpleAuthenticationInfo(user, token, getName());
    }
}
