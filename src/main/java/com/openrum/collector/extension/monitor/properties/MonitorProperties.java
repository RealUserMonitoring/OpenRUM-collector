package com.openrum.collector.extension.monitor.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lou renzheng
 */
@Data
@Component
@ConfigurationProperties(prefix = "extension.monitor.interval")
public class MonitorProperties {

    private long intervalInMilliseconds;

}
