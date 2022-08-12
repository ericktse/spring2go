package com.spring2go.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.common.core.controller.BaseController;
import com.spring2go.common.core.domain.R;
import com.spring2go.system.entity.SysConfig;
import com.spring2go.system.service.SysConfigService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 配置项
 *
 * @author xiaobin
 */
@Api(tags = "配置项")
@RequiredArgsConstructor
@RestController
@RequestMapping("/config")
public class SysConfigController extends BaseController {
    private final SysConfigService sysConfigService;

    @GetMapping("/page")
    public R list(SysConfig config,
                  @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNo,
                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        Page<SysConfig> list = sysConfigService.page(page);
        return R.ok(list);
    }
}
