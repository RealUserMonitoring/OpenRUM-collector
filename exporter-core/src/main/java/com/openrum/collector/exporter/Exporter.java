package com.openrum.collector.exporter;

public interface Exporter {

    boolean sendMessage(String jsonData,String url);
}
