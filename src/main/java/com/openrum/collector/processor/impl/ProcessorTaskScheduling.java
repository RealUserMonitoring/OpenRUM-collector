package com.openrum.collector.processor.impl;

import com.openrum.collector.exporter.DataWrapper;
import com.openrum.collector.queue.DataQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * @description: ProcessorTaskScheduling
 * @author: lou renzheng
 * @create: 2023-01-03 11:06
 **/
@Component
public class ProcessorTaskScheduling extends Thread implements Closeable {

    @Autowired
    @Qualifier("taskQueue")
    private DataQueue taskQueue;

    @Autowired
    @Qualifier("processorExecutor")
    private ExecutorService processorExecutor;

    @Override
    public void run() {
        while (true) {
            DataWrapper data = (DataWrapper) taskQueue.poll();
            if (data != null) {
                processorExecutor.submit(new ProcessDataTask(data));
            }
        }
    }

    @Override
    public void close() throws IOException {

    }

    @PostConstruct
    public void init() {
        this.start();
    }


}
