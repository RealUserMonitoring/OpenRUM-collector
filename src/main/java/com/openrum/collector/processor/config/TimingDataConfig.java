package com.openrum.collector.processor.config;

import com.openrum.collector.processor.properties.TimingDataProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class TimingDataConfig {

    @Resource
    private TimingDataProperties properties;

    private static Map<String, Integer> map = new HashMap<>();

    @PostConstruct
    private void initMapping() {
        map.put("dom_loading", properties.getDomLoading());
        map.put("dom_interactive", properties.getDomInteractive());
    }

    public Map<String, Integer> getTimingDataMapping() {
        return map;
    }
}
