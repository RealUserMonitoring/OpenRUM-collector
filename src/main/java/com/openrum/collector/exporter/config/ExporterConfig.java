package com.openrum.collector.exporter.config;

import com.openrum.collector.exporter.job.ExporterScheduling;
import com.openrum.collector.exporter.properties.ExporterProperties;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author zhaoc
 */
@Configuration
public class ExporterConfig {

    @Resource
    private ExporterProperties properties;

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(ExporterScheduling.class)
                .withIdentity("Exporter Job")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger jobTrigger() {
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(properties.getSendTimeInMilliseconds())
                .repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("Exporter Trigger")
                .withSchedule(simpleScheduleBuilder)
                .build();
    }
}
