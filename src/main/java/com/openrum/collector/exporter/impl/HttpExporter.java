package com.openrum.collector.exporter.impl;

import com.openrum.collector.exporter.Exporter;
import com.openrum.collector.exporter.properties.ExporterProperties;
import com.openrum.collector.queue.DataQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class HttpExporter implements Exporter {

    @Resource
    @Qualifier(value = "resultQueue")
    private DataQueue resultQueue;

    @Resource
    private ExporterProperties properties;

    @Override
    public boolean sendMessage(Object data) {

        return true;
    }
}
