package com.spring2go.system.feign.fallback;

import com.spring2go.common.core.domain.R;
import com.spring2go.system.entity.SysLog;
import com.spring2go.system.feign.service.RemoteDictService;
import com.spring2go.system.feign.service.RemoteLogService;
import com.spring2go.system.vo.DictModel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description: 日志服务熔断默认实现
 * @author: xiaobin
 * @date: 2021-04-06 15:01
 */
@Slf4j
@RequiredArgsConstructor
public class RemoteDictServiceFallbackImpl implements RemoteDictService {

    private final Throwable cause;

    @Override
    public R<List<DictModel>> queryDict(String code, String from) {
        log.error("feign查询字典失败 ", cause);
        return R.failed("查询字典失败，熔断");
    }

    @Override
    public R<List<DictModel>> queryDictFromTable(String table, String text, String code, String value, String from) {
        log.error("feign查询字典失败 ", cause);
        return R.failed("查询字典失败，熔断");
    }
}
