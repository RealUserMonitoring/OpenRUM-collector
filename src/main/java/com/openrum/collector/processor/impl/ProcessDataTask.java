package com.openrum.collector.processor.impl;

import com.alibaba.fastjson.JSONObject;
import com.openrum.collector.common.utils.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author zhaoc
 */
@Slf4j
public class ProcessDataTask implements Runnable {
    private final Object data;

    private TaskHandler taskHandler = SpringBeanUtils.getBean(TaskHandler.class);

    public ProcessDataTask(Object data) {
        this.data = data;
    }

    @Override
    public void run() {
        processData();
    }

    /**
     * process json data
     */
    public void processData() {
        HashMap<String, Object> map = JSONObject.parseObject(data.toString(), HashMap.class);
        int eventsSize = ((List<Map<String, Object>>) Optional.ofNullable(map.get("events")).orElse(new ArrayList<>())).size();
        log.info("Receiver accept sessionId:{} event list size:{}.", map.get("session_id"), eventsSize);
        taskHandler.filterData(map);
    }

}
