package com.spring2go.upms.biz.task;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author: xiaobin
 * @date: 2021-05-27 15:18
 */
@Component
@Slf4j
public class SampleJobHandler {

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {
        log.info("XXL-JOB, Hello World.");
    }
}
