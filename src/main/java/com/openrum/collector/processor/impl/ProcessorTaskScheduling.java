package com.openrum.collector.processor.impl;

import com.openrum.collector.exporter.DataWrapper;
import com.openrum.collector.queue.DataQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executor;

/**
 * @description: ProcessorTaskScheduling
 * @author: lou renzheng
 * @create: 2023-01-03 11:06
 **/
@Component
@Slf4j
public class ProcessorTaskScheduling extends Thread {

    @Autowired
    @Qualifier("taskQueue")
    private DataQueue taskQueue;

    @Autowired
    @Qualifier("processorExecutor")
    private Executor processorExecutor;

    private boolean RUN_TAG = true;


    @Override
    public void run() {
        while (RUN_TAG) {
            DataWrapper data = (DataWrapper) taskQueue.poll();
            if (data != null) {
                processorExecutor.execute(new ProcessDataTask(data));
            }
        }
    }

    @PostConstruct
    public void init() {
        this.start();
    }

    @PreDestroy
    public void close(){
        RUN_TAG = false;
        //todo 队列写磁盘
    }


}
