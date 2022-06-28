package com.spring2go.system.feign.service;

import com.spring2go.common.core.constant.SecurityConstants;
import com.spring2go.common.core.constant.ServiceNameConstants;
import com.spring2go.common.core.domain.R;
import com.spring2go.system.feign.factory.RemoteUserServiceFallbackFactory;
import com.spring2go.system.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * @description: 系统会员远程服务
 * @author: xiaobin
 * @date: 2021-05-12 14:44
 */
@FeignClient(contextId = "RemoteUserService", name = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserServiceFallbackFactory.class)
public interface RemoteUserService {

    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param from     来源
     * @return 结果
     */
    @GetMapping(value = "/user/info/{username}")
    R<SysUser> getInfoByUserName(@RequestParam("username") String username, @RequestHeader(SecurityConstants.FROM) String from);

    /**
     * 通过名称获取角色
     *
     * @param username 用户名
     * @param from     来源
     * @return com.spring2go.common.core.domain.R<java.util.Set < java.lang.String>>
     */
    @GetMapping(value = "/role/getRoleByUserName")
    R<Set<String>> getRoleByUserName(@RequestParam("username") String username, @RequestHeader(SecurityConstants.FROM) String from);

    /**
     * 通过名称获取权限
     *
     * @param username 用户名
     * @param from     来源
     * @return com.spring2go.common.core.domain.R<java.util.Set < java.lang.String>>
     */
    @GetMapping(value = "/role/getPermsByUserName")
    R<Set<String>> getPermsByUserName(@RequestParam("username") String username, @RequestHeader(SecurityConstants.FROM) String from);
}
