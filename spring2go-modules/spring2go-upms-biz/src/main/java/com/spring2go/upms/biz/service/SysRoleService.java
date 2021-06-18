package com.spring2go.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring2go.upms.api.entity.SysRole;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * @description: 系统角色
 * @author: xiaobin
 * @date: 2021/4/8 11:00
 */
public interface SysRoleService extends IService<SysRole> {

   List<SysRole> getRoleByUserName(String username);

   Set<String> getPermsByUserName(String username);
}
