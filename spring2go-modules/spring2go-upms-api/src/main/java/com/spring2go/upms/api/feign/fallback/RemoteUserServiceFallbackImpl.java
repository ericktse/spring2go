package com.spring2go.upms.api.feign.fallback;

import com.spring2go.common.core.domain.R;
import com.spring2go.upms.api.dto.LoginUser;
import com.spring2go.upms.api.entity.SysUser;
import com.spring2go.upms.api.feign.service.RemoteUserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 日志服务熔断默认实现
 * @author: xiaobin
 * @date: 2021-04-06 15:01
 */
@Slf4j
@RequiredArgsConstructor
public class RemoteUserServiceFallbackImpl implements RemoteUserService {

    private final Throwable cause;

    @Override
    public R<SysUser> getUserInfo(String username) {
        log.error("feign 插入日志失败", cause);
        return R.failed("熔断");
    }
}
