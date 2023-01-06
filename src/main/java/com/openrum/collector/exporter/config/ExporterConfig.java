package com.openrum.collector.exporter.config;

import com.openrum.collector.exporter.job.ExporterScheduling;
import com.openrum.collector.exporter.properties.ExporterProperties;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class ExporterConfig {

    @Resource
    private ExporterProperties config;

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(ExporterScheduling.class)
                .withIdentity("Exporter Job")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger jobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(config.getConfigTime());
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("Exporter Trigger")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
