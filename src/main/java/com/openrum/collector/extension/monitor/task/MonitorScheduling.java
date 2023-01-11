package com.openrum.collector.extension.monitor.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: lou renzheng
 * @create: 2023-01-11
 **/
@Slf4j
@Component
public class MonitorScheduling extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        long maxMemory = Runtime.getRuntime().maxMemory()/1024/1024;
        long totalMemory = Runtime.getRuntime().totalMemory()/1024/1024;
        long freeMemory = maxMemory - totalMemory;
        log.info("maxMemory:{}MB,totalMemory:{}MB,freeMemory:{}MB",maxMemory,totalMemory,freeMemory);

    }
}
