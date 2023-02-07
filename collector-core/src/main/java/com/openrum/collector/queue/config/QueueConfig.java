package com.openrum.collector.queue.config;

import com.openrum.collector.queue.DataQueue;
import com.openrum.collector.queue.domain.QueueProperties;
import com.openrum.collector.queue.impl.LocalDataQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: lou renzheng
 * @create: 2022-12-30 16:29
 **/
@Configuration
public class QueueConfig {

    @Autowired
    private QueueProperties queueProperties;

    @Bean("taskQueue")
    @ConditionalOnProperty(prefix = "queue",name = "type",havingValue = "local")
    public DataQueue processorQueue(){
        return new LocalDataQueue(queueProperties.getSize());
    }

    @Bean("resultQueue")
    @ConditionalOnProperty(prefix = "queue",name = "type",havingValue = "local")
    public DataQueue exporterQueue(){
        return new LocalDataQueue(queueProperties.getSize());
    }
}
