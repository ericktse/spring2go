package com.spring2go.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.common.core.controller.BaseController;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.DateUtils;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.common.security.util.SecurityUtils;
import com.spring2go.system.entity.SysConfig;
import com.spring2go.system.entity.SysDictData;
import com.spring2go.system.entity.SysDictType;
import com.spring2go.system.service.SysDictDataService;
import com.spring2go.system.service.SysDictTypeService;
import com.spring2go.system.vo.DictVo;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典
 *
 * @author xiaobin
 */
@Api(tags = "字典")
@RequiredArgsConstructor
@RestController
@RequestMapping("/dict")
public class SysDictController extends BaseController {
    private final SysDictDataService sysDictDataService;
    private final SysDictTypeService sysDictTypeService;

    @GetMapping("/type/list")
    public R listType() {
        List<SysDictType> list = sysDictTypeService.list();
        return R.ok(list);
    }

    @GetMapping("/type/page")
    public R pageType(DictVo dictVo,
                      @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNo,
                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<SysDictType> list = sysDictTypeService.getPage(page, dictVo);
        return R.ok(list);
    }

    @GetMapping(value = "/type/{id}")
    public R getType(@PathVariable Long id) {
        return R.ok(sysDictTypeService.getById(id));
    }

    @PostMapping("/type/add")
    public R addType(@RequestBody SysDictType dictType) {
        dictType.setCreateTime(DateUtils.now());
        dictType.setCreateBy(SecurityUtils.getUsername());
        return R.ok(sysDictTypeService.save(dictType));
    }

    @PutMapping("/type/edit")
    public R editType(@RequestBody SysDictType dictType) {
        dictType.setUpdateTime(DateUtils.now());
        dictType.setUpdateBy(SecurityUtils.getUsername());
        return R.ok(sysDictTypeService.updateById(dictType));
    }

    @DeleteMapping("/type/{id}")
    public R removeType(@PathVariable Long id) {
        return R.ok(sysDictTypeService.removeById(id));
    }

    @GetMapping(value = "/type/refreshCache")
    public R refreshCache() {

        //TODO refresh cache
        return R.ok();
    }


    @GetMapping("/data/page")
    public R list(DictVo dictVo,
                  @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNo,
                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<SysDictData> list = sysDictDataService.getPage(page, dictVo);
        return R.ok(list);
    }

    @GetMapping(value = "/data/find/{dictType}")
    public R getDictDataByType(@PathVariable String dictType) {
        List<SysDictData> data = sysDictDataService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data)) {
            data = new ArrayList<SysDictData>();
        }
        return R.ok(data);
    }

    @GetMapping(value = "/data/{id}")
    public R getData(@PathVariable Long id) {
        return R.ok(sysDictDataService.getById(id));
    }

    @PostMapping("/data/add")
    public R addData(@RequestBody SysDictData dictData) {
        dictData.setCreateTime(DateUtils.now());
        dictData.setCreateBy(SecurityUtils.getUsername());
        return R.ok(sysDictDataService.save(dictData));
    }

    @PutMapping("/data/edit")
    public R editData(@RequestBody SysDictData dictData) {
        dictData.setUpdateTime(DateUtils.now());
        dictData.setUpdateBy(SecurityUtils.getUsername());
        return R.ok(sysDictDataService.updateById(dictData));
    }

    @DeleteMapping("/data/{id}")
    public R removeData(@PathVariable Long id) {
        return R.ok(sysDictDataService.removeById(id));
    }

}
