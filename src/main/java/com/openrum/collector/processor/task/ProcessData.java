package com.openrum.collector.processor.task;

import java.util.Map;

public interface ProcessData {

    void filterData(Map<String, Object> map);
}
