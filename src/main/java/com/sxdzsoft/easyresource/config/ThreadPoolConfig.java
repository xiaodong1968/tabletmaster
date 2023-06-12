package com.sxdzsoft.easyresource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/26 10:50
 * @PackageName:com.sxdzsoft.easyresource.config
 * @ClassName: ThreadPoolConfig
 * @Description: TODO
 * @Version 1.0
 */


@Configuration
@EnableAsync
public class ThreadPoolConfig {

    private static final int CORE_POOL_SIZE = 10; // 核心线程数
    private static final int MAX_POOL_SIZE = 50; // 最大线程数
    private static final int QUEUE_CAPACITY = 100; // 线程池队列容量
    private static final String THREAD_NAME_PREFIX = "MyAsyncThread-"; // 线程名称前缀

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.initialize();
        return executor;
    }
}

