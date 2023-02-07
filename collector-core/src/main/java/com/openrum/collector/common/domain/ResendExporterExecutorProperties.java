package com.openrum.collector.common.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "executor.resend.exporter")
@Data
public class ResendExporterExecutorProperties {

    private Integer corePoolSize;

    private Integer maxPoolSize;

    private Integer queueSize;
}
