package com.openrum.collector.exporter.config;

import com.openrum.collector.exporter.job.ResendScheduling;
import com.openrum.collector.exporter.properties.ExporterResendProperties;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class ResendConfig {

    @Resource
    private ExporterResendProperties properties;

    @Bean
    public JobDetail jobDetailResend() {
        return JobBuilder.newJob(ResendScheduling.class)
                .withIdentity("Resend Job")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger jobTriggerResend() {
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(properties.getResendTimeInMilliseconds())
                .repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(jobDetailResend())
                .withIdentity("Resend Trigger")
                .withSchedule(simpleScheduleBuilder)
                .build();
    }
}
