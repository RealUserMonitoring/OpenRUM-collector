package com.openrum.collector.exporter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataWrapper {

    private String sessionId;

    private Object data;

}
