package com.openrum.collector.exporter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhaoc
 */
@Data
@Component
@ConfigurationProperties(prefix = "exporter.batch.resend")
public class ExporterResendProperties {

    private long resendTimeInMilliseconds;
}
