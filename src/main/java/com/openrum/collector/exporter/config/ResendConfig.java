package com.openrum.collector.exporter.config;

import com.openrum.collector.exporter.job.ResendScheduling;
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
public class ResendConfig {

    @Resource
    private ExporterProperties properties;

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(ResendScheduling.class)
                .withIdentity("Resend Job")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger jobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(properties.getResendConfigTime());
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("Resend Trigger")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
