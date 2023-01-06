package com.openrum.collector.processor.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author zhaoc
 */
@Slf4j
public class ProcessDataTask implements Runnable {
    private static Gson gson = new GsonBuilder().create();
    private final Object data;

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
        new TaskHandler().filterData(map);
    }

}
