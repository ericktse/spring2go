package com.spring2go.common.log.config;

import com.spring2go.common.log.aspect.SysLogAspect;
import com.spring2go.common.log.event.SysLogListener;
import com.spring2go.system.feign.service.RemoteLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 日志自动配置
 * @author: xiaobin
 * @date: 2021-04-02 17:25
 */
@EnableAsync
@RequiredArgsConstructor
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "spring2go.syslog.enabled", matchIfMissing = true)
public class LogAutoConfiguration implements AsyncConfigurer {

    private final RemoteLogService remoteLogService;

    @Bean
    public SysLogListener sysLogListener() {
        return new SysLogListener(remoteLogService);
    }

    @Bean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }

    /**
     * 自定义线程池
     *
     * @Async的默认线程池为SimpleAsyncTaskExecutor， 该线程池为每个任务新起一个线程，且默认线程数不做限制，不复用线程
     * 所以采用自定义线程池ThreadPoolTaskExecutor
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(300);
        executor.setThreadNamePrefix("Log-Executor-");
        //Task装饰器，把主流程的参数同步到子流程中
        executor.setTaskDecorator(new ContextTaskDecorator());
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 异常处理器
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
