package com.spring2go.system.controller;

import com.spring2go.common.core.controller.BaseController;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.log.annotation.Log;
import com.spring2go.system.entity.SysPost;
import com.spring2go.system.service.SysPostService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 系统岗位信息
 * @author: xiaobin
 * @date: 2021-03-31 16:58
 */
@Api("系统岗位管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/post")
public class SysPostController extends BaseController {

    private final SysPostService sysPostService;

    /**
     * 根据岗位编号获取详细信息
     */
    @GetMapping(value = "/{postId}")
    public R select(@PathVariable Integer postId) {
        return R.ok(sysPostService.getById(postId));
    }

    /**
     * 获取岗位列表
     */
    @Log("获取岗位列表")
    @GetMapping("/list")
    public R list() {
        List<SysPost> list = sysPostService.list();
        return R.ok(list);
    }

    /**
     * 新增岗位
     */
    @PostMapping
    public R add(@Validated @RequestBody SysPost post) {
        //dept.setCreateBy(SecurityUtils.getUsername());
        return R.ok(sysPostService.save(post));
    }

    /**
     * 修改岗位
     */
    @PutMapping
    public R edit(@Validated @RequestBody SysPost post) {

        //dept.setUpdateBy(SecurityUtils.getUsername());

        return R.ok(sysPostService.updateById(post));
    }

    /**
     * 删除岗位
     */
    @DeleteMapping("/{postId}")
    public R remove(@PathVariable Long postId) {
        return R.ok(sysPostService.removeById(postId));
    }
}
