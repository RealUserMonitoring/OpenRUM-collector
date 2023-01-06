package com.openrum.collector.exporter;

public interface Exporter {

    boolean sendMessage(Object data);
}
