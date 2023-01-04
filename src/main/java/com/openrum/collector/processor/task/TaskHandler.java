package com.openrum.collector.processor.task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openrum.collector.common.utils.SpringBeanUtils;
import com.openrum.collector.processor.config.TimingDataConfig;
import com.openrum.collector.processor.enums.EnumEventType;
import com.openrum.collector.processor.enums.TimingDataEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Data
@Slf4j
public class TaskHandler implements ProcessData {
    private static Gson gson = new GsonBuilder().create();

    private TimingDataConfig timingDataConfig = SpringBeanUtils.getBean(TimingDataConfig.class);

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
