package com.openrum.collector.exporter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhaoc
 */
@Data
@Component
@ConfigurationProperties(prefix = "exporter.send")
public class ExporterProperties {

    private int batchSendSize;

    private String configTime;

    private int retryTimes;

    private String url;

    private String resendConfigTime;
}
