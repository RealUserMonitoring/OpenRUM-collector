package com.openrum.collector.exporter.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.openrum.collector.exporter.DataWrapper;
import com.openrum.collector.exporter.FailBackup;
import com.openrum.collector.exporter.properties.FailBackupProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author zhaoc
 */
@Slf4j
@Component
public class FailBackupHandler implements FailBackup {
    @Resource
    private FailBackupProperties properties;

    @Resource
    private ExportAdapter exportAdapter;

    @Override
    public void backup(List<DataWrapper> list) {
        log.info("Start backup failed list, list size:{}", list.size());
        if (CollectionUtils.isNotEmpty(list)) {
            String path = properties.getLocalPath();
            File file = new File(path + "/");
            log.info("Write failed list to local disk,file path:{}", file.getPath());
            if (!file.exists()){
                file.mkdirs();
            }
            File f = new File(path + "/" + UUID.randomUUID());
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(f))) {
                DataWrapper[] arr = new DataWrapper[(list.size())];
                outputStream.writeObject(list.toArray(arr));
                outputStream.flush();
            } catch (IOException e) {
                log.error("failed to Serialize list,{}", file.getPath(), e);
            }
        }
    }

    public List<DataWrapper> deSerialization(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return Arrays.asList(((DataWrapper[]) in.readObject()));
        } catch (ClassNotFoundException | IOException e) {
            log.error("Failed to deSerialization file.", e);
        }
        return null;
    }

    public boolean resend(List<DataWrapper> list) {
        return exportAdapter.sendMessage(list);
    }

}

