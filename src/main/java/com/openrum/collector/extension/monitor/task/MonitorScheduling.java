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
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = maxMemory - totalMemory;
        log.info("maxMemory:{},totalMemory:{},freeMemory:{}",maxMemory,totalMemory,freeMemory);

    }
}
