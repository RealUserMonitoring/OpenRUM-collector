package com.openrum.collector.exporter.job;

import com.openrum.collector.exporter.DataWrapper;
import com.openrum.collector.exporter.impl.HttpExporter;
import com.openrum.collector.exporter.properties.ExporterProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExporterServer {

    @Resource
    private ExporterProperties properties;

    @Resource
    private HttpExporter httpExporter;

//    @Resource
//    private FailBackupHandler failBackup;

    public void exportData(List<DataWrapper> list) {
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        List<Object> sendList = list.stream().map(DataWrapper::getData).collect(Collectors.toList());
        boolean isSuccess = httpExporter.sendMessage(sendList);
        if (!isSuccess) {
//            failBackup.backup(list);
        }
    }

}
