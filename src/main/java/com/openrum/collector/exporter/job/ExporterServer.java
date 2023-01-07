package com.openrum.collector.exporter.job;

import com.openrum.collector.exporter.impl.HttpExporter;
import com.openrum.collector.exporter.properties.ExporterProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ExporterServer {

    @Resource
    private ExporterProperties properties;

    @Resource
    private HttpExporter httpExporter;

    public void exportData(List<Object> list) {
        httpExporter.sendMessage(list);
    }

}
