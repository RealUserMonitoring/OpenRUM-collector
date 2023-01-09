package com.openrum.collector.exporter;

import java.util.List;

public interface Exporter {

    boolean sendMessage(List<Object> data);
}
