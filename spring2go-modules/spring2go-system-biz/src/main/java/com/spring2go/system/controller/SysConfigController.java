package com.spring2go.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.common.core.controller.BaseController;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.DateUtils;
import com.spring2go.common.security.util.SecurityUtils;
import com.spring2go.system.entity.SysConfig;
import com.spring2go.system.entity.SysPosition;
import com.spring2go.system.service.SysConfigService;
import com.spring2go.system.vo.ConfigVo;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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
    public R list(ConfigVo configVo,
                  @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNo,
                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<SysConfig> list = sysConfigService.getPage(page, configVo);
        return R.ok(list);
    }

    /**
     * 查询
     */
    @GetMapping(value = "/{id}")
    public R select(@PathVariable Long id) {
        return R.ok(sysConfigService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R add(@RequestBody SysConfig config) {
        config.setCreateTime(DateUtils.now());
        config.setCreateBy(SecurityUtils.getUsername());
        return R.ok(sysConfigService.save(config));
    }

    /**
     * 修改
     */
    @PutMapping("/edit")
    public R edit(@RequestBody SysConfig config) {
        config.setUpdateTime(DateUtils.now());
        config.setUpdateBy(SecurityUtils.getUsername());
        return R.ok(sysConfigService.updateById(config));
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R remove(@PathVariable Long id) {
        return R.ok(sysConfigService.removeById(id));
    }

    @GetMapping(value = "/refreshCache")
    public R refreshCache() {

        //TODO refresh cache
        return R.ok();
    }
}
