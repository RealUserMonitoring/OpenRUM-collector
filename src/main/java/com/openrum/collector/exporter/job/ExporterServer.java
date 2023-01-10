package com.openrum.collector.exporter.job;

import com.openrum.collector.exporter.DataWrapper;
import com.openrum.collector.exporter.impl.FailBackupHandler;
import com.openrum.collector.exporter.impl.HttpExporter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaoc
 */
@Component
public class ExporterServer {

    @Resource
    private HttpExporter httpExporter;

    @Resource
    private FailBackupHandler failBackup;

    public void exportData(List<DataWrapper> list) {
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        boolean isSuccess = httpExporter.sendMessage(list);
        if (!isSuccess) {
            failBackup.backup(list);
        }
    }

}
