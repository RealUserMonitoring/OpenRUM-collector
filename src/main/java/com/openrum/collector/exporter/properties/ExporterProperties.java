package com.openrum.collector.exporter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sender.batch")
public class ExporterProperties {

    private int batchSendSize;

    private String configTime;

    private int retryTimes;
}
