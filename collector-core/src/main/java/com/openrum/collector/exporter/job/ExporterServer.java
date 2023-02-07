package com.openrum.collector.exporter.job;

import com.openrum.collector.exporter.DataWrapper;
import com.openrum.collector.exporter.impl.ExportAdapter;
import com.openrum.collector.exporter.impl.FailBackupHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhaoc
 */
@Slf4j
@Component
public class ExporterServer {

    @Resource
    private ExportAdapter exportAdapter;

    @Resource
    private FailBackupHandler failBackup;

    private static AtomicInteger atomicInteger = new AtomicInteger();

    public void exportData(List<DataWrapper> list) {
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        if (log.isDebugEnabled()) {
            list.stream().forEach(o -> atomicInteger.incrementAndGet());
            log.debug("Exporter data count:{}", atomicInteger.get());
        }
        boolean isSuccess = exportAdapter.sendMessage(list);
        if (!isSuccess) {
            failBackup.backup(list);
        }
    }

}
