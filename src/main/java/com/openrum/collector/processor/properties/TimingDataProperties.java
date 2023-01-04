package com.openrum.collector.processor.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "timing.data")
public class TimingDataProperties {

    private Integer domLoading;

    private Integer domInteractive;
}
