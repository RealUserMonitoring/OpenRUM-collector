package com.openrum.collector.processor.impl;

import com.alibaba.fastjson.JSONObject;
import com.openrum.collector.exporter.job.ExporterServer;
import com.openrum.collector.exporter.properties.ExporterProperties;
import com.openrum.collector.processor.ProcessData;
import com.openrum.collector.processor.config.TimingDataConfig;
import com.openrum.collector.processor.enums.EnumEventType;
import com.openrum.collector.processor.enums.TimingDataEnum;
import com.openrum.collector.queue.DataQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TaskHandler implements ProcessData {

    @Resource
    private TimingDataConfig timingDataConfig;

    @Resource
    @Qualifier("resultQueue")
    private DataQueue resultQueue;

    @Resource
    private ExporterProperties properties;

    @Resource
    private ExporterServer exporterServer;

    @Resource
    @Qualifier(value = "exporterExecutor")
    private ExecutorService exporterExecutor;

    @Override
    public void filterData(Map<String, Object> map) {
        try {
            String sessionId = map.get("session_id").toString();
            List<Map<String, Object>> events = (List<Map<String, Object>>) Optional.ofNullable(map.get("events")).orElse(new ArrayList<>());
            List<Map<String, Object>> eventsFilterResult = events.stream().filter(event -> isCorrectEvent(sessionId, event)).collect(Collectors.toList());
            map.put("events", eventsFilterResult);
            log.info("SessionId:{} export event list size:{}", map.get("session_id"), eventsFilterResult.size());

            batchSend(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void batchSend(Map<String, Object> map) {
        try {
            resultQueue.put(map);
            if (resultQueue.size() >= properties.getBatchSendSize()) {
                List<Object> sendList = new ArrayList<>();
                resultQueue.drainTo(sendList);
                exporterExecutor.submit(() -> exporterServer.exportData(sendList));
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
                log.info("sessionId:{}, this event is missing necessary parameters:{}.", sessionId, timingDataAttribute);
                return false;
            }
            int jsonValue = timingData.get(timingDataAttribute) != null ? Integer.parseInt(timingData.get(timingDataAttribute).toString()) : 0;
            if (jsonValue > entry.getValue()) {
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

}
