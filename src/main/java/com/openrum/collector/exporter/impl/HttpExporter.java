package com.openrum.collector.exporter.impl;

import com.alibaba.fastjson.JSON;
import com.openrum.collector.exporter.DataWrapper;
import com.openrum.collector.exporter.Exporter;
import com.openrum.collector.exporter.properties.ExporterProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaoc
 */
@Slf4j
@Component
public class HttpExporter implements Exporter {

    @Resource
    private ExporterProperties properties;

    @Resource
    private RestTemplate restTemplate;

    @Override
    public boolean sendMessage(List<DataWrapper> list) {
        String url = properties.getUrl();
        boolean isSuccess = false;
        for (int i = 1; i <= properties.getRetryTimes(); i++) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                log.info("backend url: {}", url);
                List<Object> data = list.stream().map(DataWrapper::getData).collect(Collectors.toList());
                String json = JSON.toJSONString(data);
                HttpEntity<ByteArrayResource> postEntity = new HttpEntity<>(new ByteArrayResource(json.getBytes()), headers);
                ResponseEntity<String> response = restTemplate.postForEntity(url, postEntity, String.class);
                if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                    log.info("request {}, responseBody is {}", isSuccess, response.getBody());
                    isSuccess = true;
                    break;
                } else {
                    log.info("response error,responseBody is {}", response.getBody());
                }
            } catch (Exception e) {
                log.error("exporter request failed at {} times,total {} times,reason: {}", i, properties.getRetryTimes(), e);
            }
        }
        return isSuccess;
    }

}
