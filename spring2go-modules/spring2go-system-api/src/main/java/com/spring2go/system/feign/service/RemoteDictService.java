package com.spring2go.system.feign.service;

import com.spring2go.common.core.constant.SecurityConstants;
import com.spring2go.common.core.constant.ServiceNameConstants;
import com.spring2go.common.core.domain.R;
import com.spring2go.system.feign.factory.RemoteLogServiceFallbackFactory;
import com.spring2go.system.vo.DictModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 字典远程服务
 *
 * @author xiaobin
 */
@FeignClient(contextId = "RemoteDictService", name = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteLogServiceFallbackFactory.class)
public interface RemoteDictService {

    /**
     * 查询字典
     *
     * @param code 字典code
     * @param from 来源
     * @return
     */
    @PostMapping("/dict/queryDict")
    R<List<DictModel>> queryDict(@RequestParam("code") String code, @RequestHeader(SecurityConstants.FROM) String from);

    /**
     * 从table查询字典
     *
     * @param table 表名词
     * @param code  字典code
     * @param text  字典text
     * @param value 字典value
     * @param from  来源
     * @return
     */
    @PostMapping("/dict/queryDictFromTable")
    R<List<DictModel>> queryDictFromTable(@RequestParam("table") String table, @RequestParam("code") String code, @RequestParam("text") String text, @RequestParam("value") String value, @RequestHeader(SecurityConstants.FROM) String from);
}
