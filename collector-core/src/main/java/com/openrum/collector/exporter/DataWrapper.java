package com.openrum.collector.exporter;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class DataWrapper implements Serializable {

    private String sessionId;

    private Object data;

}
