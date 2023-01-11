package com.openrum.collector.exporter.job;

import com.openrum.collector.common.domain.ResendExporterExecutorProperties;
import com.openrum.collector.exporter.DataWrapper;
import com.openrum.collector.exporter.impl.FailBackupHandler;
import com.openrum.collector.exporter.properties.FailBackupProperties;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * @author zhaoc
 */
@Slf4j
@Component
@DisallowConcurrentExecution
public class ResendScheduling extends QuartzJobBean {
    private static final int COUNT = 10;

    @Resource
    private FailBackupProperties properties;

    @Resource
    private FailBackupHandler handler;

    @Resource
    @Qualifier(value = "resendExporterExecutor")
    private Executor resendExporterExecutor;

    @Resource
    private ResendExporterExecutorProperties resendProperties;

    @Resource
    private FailBackupHandler failBackupHandler;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            List<File> files = new ArrayList<>(100);
            getFileList(properties.getLocalPath(), files);
            int count = 0;
            CountDownLatch countDownLatch = new CountDownLatch(resendProperties.getCorePoolSize());
            Iterator<File> iterator = files.iterator();
            while (iterator.hasNext()) {
                File file = iterator.next();
                List<DataWrapper> list = handler.deSerialization(file.getAbsolutePath());
                if (CollectionUtils.isEmpty(list)) {
                    log.warn("File content is empty.");
                    continue;
                }
                iterator.remove();
                CountDownLatch finalCountDownLatch = countDownLatch;
                resendExporterExecutor.execute(() -> {
                    boolean isSuccess = resend(list, finalCountDownLatch);
                    if (isSuccess) {
                        deleteFile(file);
                    }
                });
                count++;
                if (count % resendProperties.getCorePoolSize() == 0) {
                    countDownLatch.await();
                    countDownLatch = new CountDownLatch(resendProperties.getCorePoolSize());
                }
            }
        } catch (Exception e) {
            log.error("resend monitoring data", e);
        }
        log.info("resend monitoring data job end.");
    }

    private boolean resend(List<DataWrapper> list, CountDownLatch countDownLatch) {
        try {
            return failBackupHandler.resend(list);
        } catch (Exception e) {
            log.error("Retry export data fail.", e);
        } finally {
            countDownLatch.countDown();
        }
        return false;
    }

    private void deleteFile(File file) {
        boolean delete = file.delete();
        if (!delete) {
            log.error("failed to ,文件名字为:{}", file.getName());
            int count = 0;
            do {
                if (count == COUNT) {
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    log.error("delete file", e);
                }
                delete = file.delete();
                count++;
            } while (!delete);
        }
    }

    private void getFileList(String strPath, List<File> fileList) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    getFileList(file.getAbsolutePath(), fileList);
                } else {
                    fileList.add(file);
                }
            }
        }
    }

}
