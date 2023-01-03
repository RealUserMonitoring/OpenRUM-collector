package com.openrum.collector.common.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: lou renzheng
 * @create: 2023-01-03 18:12
 **/
@Component
@ConfigurationProperties(prefix = "executor.processor")
@Data
public class ProcessorExecutorProperties {

    private Integer corePoolSize;

    private Integer maxPoolSize;

    private Integer queueSize;
}
