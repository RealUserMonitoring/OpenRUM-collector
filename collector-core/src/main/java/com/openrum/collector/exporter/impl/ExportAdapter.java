package com.openrum.collector.exporter.impl;

import com.alibaba.fastjson.JSON;
import com.openrum.collector.exporter.DataWrapper;
import com.openrum.collector.exporter.Exporter;
import com.openrum.collector.exporter.properties.ExporterProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: Adapter
 * @author: lou renzheng
 * @create: 2023-02-06
 **/
@Component
@Slf4j
public class ExportAdapter {

    @Autowired
    @Qualifier("exporterList")
    private List<Exporter> exporterList;

    @Resource
    private ExporterProperties properties;


    public boolean sendMessage(List<DataWrapper> list) {
        boolean isSuccess = false;
        List<Object> data = list.stream().map(DataWrapper::getData).collect(Collectors.toList());
        String json = JSON.toJSONString(data);
        for (Exporter exporter : exporterList) {
            isSuccess = send(exporter,json);

        }
        return isSuccess;

    }

    private boolean send(Exporter exporter,String json) {
        boolean isSuccess = false;
        for (int i = 1; i <= properties.getRetryTimes(); i++) {
            try {
                isSuccess = exporter.sendMessage(json,properties.getUrl());
                if (isSuccess) {
                    break;
                }
            } catch (Exception e) {
                log.error("Exporter request failed at {} times,total {} times,reason: {}", i, properties.getRetryTimes(), e);
            }
        }
        return isSuccess;
    }
}
