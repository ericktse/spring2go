package com.spring2go.common.security.util;

import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.redis.util.RedisUtils;
import com.spring2go.common.security.domain.SimpleAuthorizeUser;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description: Token工具
 * @author: xiaobin
 * @date: 2021-05-13 14:25
 */
@RequiredArgsConstructor
public class TokenUtils {


    private final RedisUtils redisUtils;

    /**
     * @description: 单位：秒，一小时，1*60*60=3600
     */
    private final static long TOKEN_EXPIRE_TIME = 3600;

    private final static String ACCESS_TOKEN_KEY = "access_token_";

    public String createToken(String username, Object originalUser, Set<String> roles, Set<String> permissions) {
        //TODO：实现JWT Token实现

        // 生成token
        String token = UUID.randomUUID().toString();

        SimpleAuthorizeUser authorizationUser = new SimpleAuthorizeUser(username, originalUser, roles, permissions);

        redisUtils.setCacheObject(ACCESS_TOKEN_KEY + token, authorizationUser, TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        return token;
    }

    public void refreshToken(String token) {
        redisUtils.expire(ACCESS_TOKEN_KEY + token, TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    public SimpleAuthorizeUser getAuthorizationUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            SimpleAuthorizeUser user = redisUtils.getCacheObject(ACCESS_TOKEN_KEY + token);
            return user;
        }
        return null;
    }
}
