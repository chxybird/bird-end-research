package com.bird.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @Author lipu
 * @Date 2021/9/11 13:22
 * @Description 线程池配置类
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * @Author lipu
     * @Date 2021/9/11 13:57
     * @Description 定时任务线程池 底层使用的还是周期性线程池
     */
    @Bean
    public ThreadPoolTaskScheduler ThreadPoolTaskScheduler(){
        ThreadPoolTaskScheduler scheduler=new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(20);
        scheduler.initialize();
        return scheduler;
    }
}
