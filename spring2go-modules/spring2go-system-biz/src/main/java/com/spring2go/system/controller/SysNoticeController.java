package com.spring2go.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.common.core.controller.BaseController;
import com.spring2go.common.core.domain.R;
import com.spring2go.system.entity.SysConfig;
import com.spring2go.system.entity.SysNotice;
import com.spring2go.system.service.SysConfigService;
import com.spring2go.system.service.SysNoticeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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
    public R list(SysNotice config,
                  @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNo,
                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        Page<SysNotice> list = sysNoticeService.page(page);
        return R.ok(list);
    }
}
