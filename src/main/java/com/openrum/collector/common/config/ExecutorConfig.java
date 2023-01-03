package com.openrum.collector.common.config;

import com.openrum.collector.common.domain.ExporterExecutorProperties;
import com.openrum.collector.common.domain.ProcessorExecutorProperties;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ExecutorConfig {

  @Autowired
  private ProcessorExecutorProperties processorExecutorProperties;

  @Autowired
  private ExporterExecutorProperties exporterExecutorProperties;


  @Bean("processorExecutor")
  public ExecutorService processorExecutor() {
    ExecutorService threadPoolExecutor = new ThreadPoolExecutor(processorExecutorProperties.getCorePoolSize(),
            processorExecutorProperties.getMaxPoolSize(), 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(processorExecutorProperties.getQueueSize()));
    return threadPoolExecutor;
  }

  @Bean("exporterExecutor")
  public ExecutorService exporterExecutor() {
    ExecutorService threadPoolExecutor = new ThreadPoolExecutor(exporterExecutorProperties.getCorePoolSize(),
            exporterExecutorProperties.getMaxPoolSize(), 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(exporterExecutorProperties.getQueueSize()));
    return threadPoolExecutor;
  }






}
