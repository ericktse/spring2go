package com.spring2go.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring2go.common.core.controller.BaseController;
import com.spring2go.common.core.domain.R;
import com.spring2go.common.core.util.StringUtils;
import com.spring2go.system.entity.SysDictData;
import com.spring2go.system.entity.SysDictType;
import com.spring2go.system.service.SysDictDataService;
import com.spring2go.system.service.SysDictTypeService;
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


    @GetMapping("/type/page")
    public R pageType(SysDictType dictType,
                      @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNo,
                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        Page<SysDictType> list = sysDictTypeService.page(page);
        return R.ok(list);
    }

    @GetMapping(value = "/type/{dictId}")
    public R getInfo(@PathVariable Long dictId) {
        return R.ok(sysDictTypeService.getById(dictId));
    }

    @GetMapping("/data/page")
    public R list(SysDictData dictData,
                  @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNo,
                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        Page<SysDictData> list = sysDictDataService.page(page);
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

}
