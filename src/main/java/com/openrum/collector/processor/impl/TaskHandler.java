package com.openrum.collector.processor.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openrum.collector.exporter.job.ExporterServer;
import com.openrum.collector.exporter.properties.ExporterProperties;
import com.openrum.collector.processor.ProcessData;
import com.openrum.collector.processor.config.TimingDataConfig;
import com.openrum.collector.processor.enums.EnumEventType;
import com.openrum.collector.processor.enums.TimingDataEnum;
import com.openrum.collector.queue.DataQueue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
@Component
public class TaskHandler implements ProcessData {
    private static Gson gson = new GsonBuilder().create();

    @Resource
    private TimingDataConfig timingDataConfig;

    @Resource
    @Qualifier(value = "resultQueue")
    private DataQueue resultQueue;

    @Resource
    private ExporterProperties properties;

    @Resource
    private ExporterServer server;

    @Override
    public void filterData(Map<String, Object> map) {

    }

    public boolean hasTimingData(String eventType) {
        if (EnumEventType.VIEW.getEventType().equals(eventType) || EnumEventType.RESOURCE.getEventType().equals(eventType)) {
            return true;
        }
        return false;
    }

    public String getTimingDataAttributeKey(String eventType) {
        if (EnumEventType.VIEW.getEventType().equals(eventType)) {
            return TimingDataEnum.VIEW_LOAD_TIMING_DATA.getAttribute();
        } else if (EnumEventType.RESOURCE.getEventType().equals(eventType)) {
            return TimingDataEnum.RESOURCE_TIMING_DATA.getAttribute();
        }
        return null;
    }


}
