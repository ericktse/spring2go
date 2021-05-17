package com.spring2go.upms.biz.controller;

import com.spring2go.common.core.domain.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 通用控制器
 * @author: xiaobin
 * @date: 2021-05-14 11:18
 */
@RestController
@RequestMapping("/sys/common")
public class CommonController {
    @GetMapping("/403")
    public R<?> noAuth() {
        return R.failed("没有权限，请联系管理员授权");
    }

}
