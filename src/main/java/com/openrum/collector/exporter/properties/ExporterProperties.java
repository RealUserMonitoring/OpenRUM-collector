package com.openrum.collector.exporter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhaoc
 */
@Data
@Component
@ConfigurationProperties(prefix = "exporter.batch.send")
public class ExporterProperties {

    private int sendSize;

    private long sendTimeInMilliseconds;

    private int retryTimes;

    private String url;

    private long resendTimeInMilliseconds;
}
