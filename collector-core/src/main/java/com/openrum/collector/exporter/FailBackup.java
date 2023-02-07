package com.openrum.collector.exporter;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FailBackup {

    /**
     * Back up failed export data
     * @param list
     */
    void backup(List<DataWrapper> list);
}
