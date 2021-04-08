package com.spring2go.upms.biz.controller;

import com.spring2go.common.core.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 系统角色
 * @author: xiaobin
 * @date: 2021-04-08 11:15
 */
@Api("菜单角色模块")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {
}
