package com.spring2go.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.common.core.controller.BaseController;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.DateUtils;
import com.spring2go.common.core.util.excel.ExcelUtils;
import com.spring2go.common.log.annotation.Log;
import com.spring2go.common.mybatis.util.QueryWrapperUtils;
import com.spring2go.common.security.util.SecurityUtils;
import com.spring2go.system.entity.SysPosition;
import com.spring2go.system.service.SysPositionService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description: 系统岗位信息
 * @author: xiaobin
 * @date: 2021-03-31 16:58
 */
@Api(tags = "岗位管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class SysPositionController extends BaseController {

    private final SysPositionService sysPositionService;

    /**
     * 根据岗位编号获取详细信息
     */
    @GetMapping(value = "/{id}")
    public R select(@PathVariable Long id) {
        return R.ok(sysPositionService.getById(id));
    }

    /**
     * 获取岗位列表
     */
    @Log("获取岗位列表")
    @GetMapping("/list")
    public R list() {
        List<SysPosition> list = sysPositionService.list();
        return R.ok(list);
    }

    /**
     * 分页查询角色信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R getPage(Page page, SysPosition post) {
        return R.ok(sysPositionService.getPage(page, post));
    }


    /**
     * 新增岗位
     */
    @PostMapping("/add")
    public R add(@Validated @RequestBody SysPosition post) {
        post.setCreateTime(DateUtils.now());
        post.setCreateBy(SecurityUtils.getUsername());
        return R.ok(sysPositionService.save(post));
    }

    /**
     * 修改岗位
     */
    @PutMapping("/edit")
    public R edit(@Validated @RequestBody SysPosition post) {

        post.setUpdateTime(DateUtils.now());
        post.setUpdateBy(SecurityUtils.getUsername());

        return R.ok(sysPositionService.updateById(post));
    }

    /**
     * 删除岗位
     */
    @DeleteMapping("/{id}")
    public R remove(@PathVariable Long id) {
        return R.ok(sysPositionService.removeById(id));
    }

    @Log("岗位数据导出")
    @PostMapping("/exportExcel")
    public void export(HttpServletRequest request, HttpServletResponse response, SysPosition post) {
        QueryWrapper<SysPosition> wrapper = QueryWrapperUtils.initQueryWrapper(post, request.getParameterMap());
        List<SysPosition> list = sysPositionService.list(wrapper);
        ExcelUtils<SysPosition> util = new ExcelUtils<>(SysPosition.class);
        util.exportExcel(response, list);
    }
}
