package com.lab3.zalotov.laba3;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@SpringBootApplication
public class Laba3Application {

    private final static Logger logger = Logger.getLogger(Laba3Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Laba3Application.class, args);
    }

    private String threadName;
    private int corePoolSize;
    private int maxPoolSize;
    private int queueCapacity;

    @Autowired
    public void setThreadName(@Value("${thread.name}") String threadName) {
        this.threadName = threadName;
    }

    @Autowired
    public void setCorePoolSize(@Value("${core.pool.size}") int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    @Autowired
    public void setMaxPoolSize(@Value("${max.pool.size}") int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    @Autowired
    public void setQueueCapacity(@Value("${queue.capacity}") int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    @Bean(name = "taskExecutor")
    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix(threadName);
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.afterPropertiesSet();
        logger.info("Initialize taskExecutor");
        return executor;
    }
}
