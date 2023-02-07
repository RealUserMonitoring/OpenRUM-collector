package com.openrum.collector.queue.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: MemoryInfo
 * @author: lou renzheng
 * @create: 2022-12-29
 **/
@Component
@ConfigurationProperties(prefix = "queue")
@Data
public class QueueProperties {

    private Integer size;

    private String type;

}
