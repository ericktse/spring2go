package com.spring2go.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.common.core.controller.BaseController;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.DateUtils;
import com.spring2go.common.security.util.SecurityUtils;
import com.spring2go.system.entity.SysConfig;
import com.spring2go.system.entity.SysNotice;
import com.spring2go.system.service.SysConfigService;
import com.spring2go.system.service.SysNoticeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 系统通知
 *
 * @author xiaobin
 */
@Api(tags = "系统通知")
@RequiredArgsConstructor
@RestController
@RequestMapping("/notice")
public class SysNoticeController extends BaseController {
    private final SysNoticeService sysNoticeService;

    @GetMapping("/page")
    public R list(SysNotice notice,
                  @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNo,
                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<SysNotice> list = sysNoticeService.getPage(page, notice);
        return R.ok(list);
    }

    /**
     * 查询
     */
    @GetMapping(value = "/{id}")
    public R select(@PathVariable Long id) {
        return R.ok(sysNoticeService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R add(@RequestBody SysNotice notice) {
        notice.setCreateTime(DateUtils.now());
        notice.setCreateBy(SecurityUtils.getUsername());
        return R.ok(sysNoticeService.save(notice));
    }

    /**
     * 修改
     */
    @PutMapping("/edit")
    public R edit(@RequestBody SysNotice notice) {
        notice.setUpdateTime(DateUtils.now());
        notice.setUpdateBy(SecurityUtils.getUsername());
        return R.ok(sysNoticeService.updateById(notice));
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R remove(@PathVariable Long id) {
        return R.ok(sysNoticeService.removeById(id));
    }

}
