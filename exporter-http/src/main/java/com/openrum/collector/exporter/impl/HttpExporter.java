package com.openrum.collector.exporter.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.openrum.collector.exporter.Exporter;

/**
 * @author zhaoc
 */
public class HttpExporter implements Exporter {

    @Override
    public boolean sendMessage(String jsonData,String url) {
        boolean isSuccess = false;
        HttpResponse httpResponse = HttpUtil.createPost(url).body(jsonData.getBytes()).execute();
        if (httpResponse.isOk()) {
            isSuccess = true;
        }

        return isSuccess;
    }

}
