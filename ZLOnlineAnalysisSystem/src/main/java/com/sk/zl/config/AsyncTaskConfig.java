package com.sk.zl.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Description : 线程配置类
 * @Author : Ellie
 * @Date : 2019/3/6
 */
@Configuration
@EnableAsync
@ComponentScan("com.sk.zl.service")
public class AsyncTaskConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(5);
        threadPool.setMaxPoolSize(20);
        threadPool.setQueueCapacity(10);
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        threadPool.setAwaitTerminationSeconds(60);
        threadPool.setThreadNamePrefix("SkAsync-");
        threadPool.initialize();
        return threadPool;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
