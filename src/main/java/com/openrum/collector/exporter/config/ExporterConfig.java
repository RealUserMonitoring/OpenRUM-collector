package com.openrum.collector.exporter.config;

import com.openrum.collector.exporter.Exporter;
import com.openrum.collector.exporter.job.ExporterScheduling;
import com.openrum.collector.exporter.properties.ExporterProperties;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

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

    @Bean
    @Qualifier("exporterList")
    public List<Exporter> ExporterConfigure() {
        List<Exporter> exporterList = new ArrayList<>();
        ServiceLoader<Exporter> serviceLoader = ServiceLoader.load(Exporter.class);
        Iterator<Exporter> iterator = serviceLoader.iterator();
        while(iterator.hasNext()){
            Exporter exporter = iterator.next();
            if(properties.getClients().contains(exporter.getClass().getName())){
                exporterList.add(exporter);
            }
        }
        if(CollectionUtils.isEmpty(exporterList)){
            throw new IllegalStateException("exporter non-existent");
        }
        return exporterList;
    }
}
