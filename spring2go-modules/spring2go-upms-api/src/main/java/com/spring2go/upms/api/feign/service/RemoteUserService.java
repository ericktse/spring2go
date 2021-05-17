package com.spring2go.upms.api.feign.service;

import com.spring2go.common.core.constant.ServiceNameConstants;
import com.spring2go.common.core.domain.R;
import com.spring2go.upms.api.dto.LoginUser;
import com.spring2go.upms.api.entity.SysUser;
import com.spring2go.upms.api.feign.factory.RemoteUserServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * @description: 系统会员远程服务
 * @author: xiaobin
 * @date: 2021-05-12 14:44
 */
@FeignClient(contextId = "RemoteUserService", name = ServiceNameConstants.UPMS_SERVICE, fallbackFactory = RemoteUserServiceFallbackFactory.class)
public interface RemoteUserService {

    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 结果
     */
    @GetMapping(value = "/sys/user/getInfoByUserName")
    R<SysUser> getInfoByUserName(@RequestParam("username") String username);

    @GetMapping(value = "/sys/role/getRoleByUserName")
    R<Set<String>> getRoleByUserName(@RequestParam("username") String username);

    @GetMapping(value = "/sys/role/getPermsByUserName")
    R<Set<String>> getPermsByUserName(@RequestParam("username") String username);
}
