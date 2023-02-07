package com.openrum.collector.processor.impl;

import com.openrum.collector.common.utils.SpringBeanUtils;
import com.openrum.collector.exporter.DataWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhaoc
 */
@Slf4j
public class ProcessDataTask implements Runnable {
    private final DataWrapper data;

    private TaskHandler taskHandler = SpringBeanUtils.getBean(TaskHandler.class);

    public ProcessDataTask(DataWrapper data) {
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
        taskHandler.filterData(data);
    }

}
