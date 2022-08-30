package com.spring2go.common.security.util;

import com.spring2go.common.security.domain.SimpleAuthorizeUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * 安全工具类
 *
 * @author: xiaobin
 * @date: 2021-06-18 10:58
 */
@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final TokenUtils tokenUtils;

    private static TokenUtils TOKENUTILS;

    //@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，
    // 并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行
    @PostConstruct
    public void init() {
        SecurityUtils.TOKENUTILS = tokenUtils;
    }


    /**
     * 获取Authentication
     */
    public static SimpleAuthorizeUser getAuthentication() {
        String token = TokenUtils.getTokenByRequest();
        return TOKENUTILS.getAuthorizeUser(token);
    }

    /**
     * 获取用户名
     */
    public static Integer getUserId() {
        SimpleAuthorizeUser user = getAuthentication();
        if (null != user) {
            return user.getUserId();
        }
        return null;
    }

    /**
     * 获取用户名
     */
    public static String getUsername() {
        SimpleAuthorizeUser user = getAuthentication();
        if (null != user) {
            return user.getUsername();
        }
        return null;
    }

    /**
     * 获取用户
     */
    public static Object getUser() {
        SimpleAuthorizeUser user = getAuthentication();
        if (null != user) {
            return user.getOriginalUser();
        }
        return null;
    }

    /**
     * 获取用户角色信息
     *
     * @return 角色集合
     */
    public static Set<String> getRoles() {
        SimpleAuthorizeUser user = getAuthentication();
        if (null != user) {
            return user.getRoles();
        }

        return null;
    }

    /**
     * 获取用户权限信息
     *
     * @return 角色集合
     */
    public static Set<String> getPermissions() {
        SimpleAuthorizeUser user = getAuthentication();
        if (null != user) {
            return user.getPermissions();
        }

        return null;
    }

    /**
     * 是否为管理员
     *
     * @param roleName 用户名称
     * @return 结果
     */
    public static boolean isAdmin(String roleName) {
        return roleName != null && "超级管理员".equals(roleName);
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Integer userId) {
        return userId != null && 1 == userId;
    }
}
