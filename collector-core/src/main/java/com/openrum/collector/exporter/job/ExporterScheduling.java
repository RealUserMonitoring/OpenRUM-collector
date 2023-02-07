package com.openrum.collector.exporter.job;

import com.openrum.collector.exporter.DataWrapper;
import com.openrum.collector.queue.AbstractDataQueue;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoc
 */
@Slf4j
@Component
public class ExporterScheduling extends QuartzJobBean {

    @Resource
    @Qualifier("resultQueue")
    private AbstractDataQueue<?> resultQueue;

    @Resource
    private ExporterServer exporterServer;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        log.info("Scheduling export monitoring data to backend, current list size:{}", resultQueue.size());
        if (!resultQueue.isEmpty()) {
            List<DataWrapper> sendList = new ArrayList<>();
            resultQueue.drainTo(sendList);
            exporterServer.exportData(sendList);
        }
    }
}
