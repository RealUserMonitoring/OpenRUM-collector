package com.openrum.collector.common.config;

import com.openrum.collector.common.domain.ExporterExecutorProperties;
import com.openrum.collector.common.domain.ProcessorExecutorProperties;
import com.openrum.collector.common.domain.ResendExporterExecutorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * @description: BaseExecutor
 * @author: lou renzheng
 * @create: 2022/12/30
 **/
@Configuration
public class ExecutorConfig {

    @Autowired
    private ProcessorExecutorProperties processorExecutorProperties;

    @Autowired
    private ExporterExecutorProperties exporterExecutorProperties;

    @Resource
    private ResendExporterExecutorProperties resendProperties;


    @Bean("processorExecutor")
    public Executor processorExecutor() {
        ThreadPoolTaskExecutor threadPoolExecutor = createExecutor(
                processorExecutorProperties.getCorePoolSize(),
                processorExecutorProperties.getMaxPoolSize(),
                processorExecutorProperties.getQueueSize());
        return threadPoolExecutor;
    }


    @Bean("exporterExecutor")
    public Executor exporterExecutor() {
        ThreadPoolTaskExecutor threadPoolExecutor = createExecutor(
                exporterExecutorProperties.getCorePoolSize(),
                exporterExecutorProperties.getMaxPoolSize(),
                exporterExecutorProperties.getQueueSize());
        return threadPoolExecutor;
    }

    @Bean("resendExporterExecutor")
    public Executor resendExporterExecutor() {
        ThreadPoolTaskExecutor threadPoolExecutor = createExecutor(
                resendProperties.getCorePoolSize(),
                resendProperties.getMaxPoolSize(),
                resendProperties.getQueueSize());
        return threadPoolExecutor;
    }

    private ThreadPoolTaskExecutor createExecutor(Integer processorExecutorProperties, Integer processorExecutorProperties1, Integer processorExecutorProperties2) {
        ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();
        threadPoolExecutor.setCorePoolSize(processorExecutorProperties);
        threadPoolExecutor.setMaxPoolSize(processorExecutorProperties1);
        threadPoolExecutor.setQueueCapacity(processorExecutorProperties2);
        threadPoolExecutor.setKeepAliveSeconds(5);
        threadPoolExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolExecutor.setAwaitTerminationSeconds(10);
        threadPoolExecutor.initialize();
        return threadPoolExecutor;
    }


}
