package com.openrum.collector.processor;

import com.openrum.collector.exporter.DataWrapper;

public interface ProcessData {

    void filterData(DataWrapper data);
}
