package com.openrum.collector.extension.monitor.config;

import com.openrum.collector.extension.monitor.properties.MonitorProperties;
import com.openrum.collector.extension.monitor.task.MonitorScheduling;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author zhaoc
 */
@Configuration
public class MonitorConfig {

    @Resource
    private MonitorProperties properties;

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(MonitorScheduling.class)
                .withIdentity("Monitor Job")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger jobTrigger() {
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(properties.getIntervalInMilliseconds())
                .repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("Monitor Trigger")
                .withSchedule(simpleScheduleBuilder)
                .build();
    }
}
