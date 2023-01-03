package com.openrum.collector.common.executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
*
*
* @description: BaseExecutor
*
* @author: lou renzheng
*
* @create: 2022/12/30
**/
@Configuration
public class BaseExecutor {


  @Value("${executor.processor.corePoolSize:3}")
  private Integer processorCorePoolSize;

  @Value("${executor.processor.maxPoolSize:6}")
  private Integer processorMaxPoolSize;

  @Value("${executor.processor.queueSize:2000}")
  private Integer processorQueueSize;

  @Value("${executor.exporter.corePoolSize:3}")
  private Integer exporterCorePoolSize;

  @Value("${executor.exporter.maxPoolSize:6}")
  private Integer exporterMaxPoolSize;

  @Value("${executor.exporter.queueSize:2000}")
  private Integer exporterQueueSize;


  @Bean("processorExecutor")
  public ExecutorService processorExecutor() {
    ExecutorService threadPoolExecutor = new ThreadPoolExecutor(processorCorePoolSize,
            processorMaxPoolSize, 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(processorQueueSize));
    return threadPoolExecutor;
  }

  @Bean("exporterExecutor")
  public ExecutorService exporterExecutor() {
    ExecutorService threadPoolExecutor = new ThreadPoolExecutor(exporterCorePoolSize,
            exporterMaxPoolSize, 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(exporterQueueSize));
    return threadPoolExecutor;
  }






}
