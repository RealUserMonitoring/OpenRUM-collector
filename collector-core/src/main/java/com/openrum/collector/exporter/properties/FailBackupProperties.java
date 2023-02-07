package com.openrum.collector.exporter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "fail.backup")
public class FailBackupProperties {

    private String localPath;

}



