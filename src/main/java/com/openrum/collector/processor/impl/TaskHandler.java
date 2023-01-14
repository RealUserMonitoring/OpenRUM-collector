package com.openrum.collector.processor.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.openrum.collector.exporter.DataWrapper;
import com.openrum.collector.exporter.job.ExporterServer;
import com.openrum.collector.exporter.properties.ExporterProperties;
import com.openrum.collector.processor.ProcessData;
import com.openrum.collector.processor.config.TimingDataConfig;
import com.openrum.collector.processor.enums.EnumEventType;
import com.openrum.collector.processor.enums.TimingDataEnum;
import com.openrum.collector.queue.AbstractDataQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TaskHandler implements ProcessData {

    @Resource
    private TimingDataConfig timingDataConfig;

    @Resource
    @Qualifier("resultQueue")
    private AbstractDataQueue resultQueue;

    @Resource
    private ExporterProperties properties;

    @Resource
    private ExporterServer exporterServer;

    @Resource
    @Qualifier(value = "exporterExecutor")
    private Executor exporterExecutor;

    @Override
    public void filterData(DataWrapper data) {
        try {
            String sessionId = data.getSessionId();
            HashMap<String, Object> map = (HashMap<String, Object>) data.getData();
            List<Map<String, Object>> events = (List<Map<String, Object>>) Optional.ofNullable(map.get("events")).orElse(new ArrayList<>());
            log.info("Before filtering sessionId:{} event list size:{}.", sessionId, events.size());

            List<Map<String, Object>> eventsFilterResult = events.stream()
                    .filter(event -> isCorrectEvent(sessionId, event)).collect(Collectors.toList());
            map.put("events", eventsFilterResult);
            log.info("After filtering sessionId:{} event list size:{}", sessionId, eventsFilterResult.size());
            data.setData(map);
            batchSend(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void batchSend(DataWrapper data) {
        try {
            resultQueue.put(data);
            synchronized (TaskHandler.class) {
                if (resultQueue.size() >= properties.getSendSize()) {
                    List<DataWrapper> sendList = new ArrayList<>();
                    resultQueue.drainTo(sendList);
                    exporterExecutor.execute(() -> exporterServer.exportData(sendList));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isCorrectEvent(String sessionId, Map<String, Object> event) {
        String eventType = event.get("event_type") != null ? event.get("event_type").toString() : "";
        if (hasTimingData(eventType)) {
            return checkTimingData(sessionId, event);
        }
        return true;
    }

    public boolean hasTimingData(String eventType) {
        return EnumEventType.VIEW.getEventType().equals(eventType) || EnumEventType.RESOURCE.getEventType().equals(eventType);
    }

    /**
     * check timing data
     * If the time data in the event is greater than the configured value, the event will be filter out.
     *
     * @param event event
     */
    public boolean checkTimingData(String sessionId, Map<String, Object> event) {
        String attributeKey = getTimingDataAttributeKey(event.get("event_type").toString());
        Map<String, Object> timingData = JSONObject.parseObject(event.get(attributeKey).toString(), Map.class);
        for (Map.Entry<String, Integer> entry : timingDataConfig.getTimingDataMapping().entrySet()) {
            String timingDataAttribute = entry.getKey();
            if (timingData.get(timingDataAttribute) == null) {
                log.info("Filtering sessionId:{}, this event is missing necessary parameters:{}, delete event:{}", sessionId, timingDataAttribute, JSON.toJSON(event));
                return false;
            }
            int jsonValue = timingData.get(timingDataAttribute) != null ? Integer.parseInt(timingData.get(timingDataAttribute).toString()) : 0;
            if (jsonValue > entry.getValue()) {
                log.info("Filtering sessionId:{}, timing_data:{} is incorrect, delete event:{}", sessionId, timingDataAttribute, JSON.toJSON(event));
                return false;
            }
        }
        return true;
    }

    public String getTimingDataAttributeKey(String eventType) {
        if (EnumEventType.VIEW.getEventType().equals(eventType)) {
            return TimingDataEnum.VIEW_LOAD_TIMING_DATA.getAttribute();
        } else if (EnumEventType.RESOURCE.getEventType().equals(eventType)) {
            return TimingDataEnum.RESOURCE_TIMING_DATA.getAttribute();
        }
        return null;
    }

    @PreDestroy
    public void close() {
        if (resultQueue.size() == 0) {
            return;
        }
        //retry send
        List<DataWrapper> sendList = new ArrayList<>();
        resultQueue.drainTo(sendList);
        exporterExecutor.execute(() -> exporterServer.exportData(sendList));
    }

}
