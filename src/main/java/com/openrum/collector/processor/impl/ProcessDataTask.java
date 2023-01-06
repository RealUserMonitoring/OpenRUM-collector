package com.openrum.collector.processor.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openrum.collector.common.utils.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author zhaoc
 */
@Slf4j
public class ProcessDataTask implements Runnable {
    private static Gson gson = new GsonBuilder().create();
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
        HashMap<String, Object> map = gson.fromJson(data.toString(), HashMap.class);
        taskHandler.filterData(map);
    }

}
