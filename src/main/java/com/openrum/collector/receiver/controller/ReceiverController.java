package com.openrum.collector.receiver.controller;

import com.alibaba.fastjson.JSONObject;
import com.openrum.collector.common.domain.Result;
import com.openrum.collector.exporter.DataWrapper;
import com.openrum.collector.queue.DataQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @description: receiver controller
 * @author: lou renzheng
 * @create: 2022-12-30 10:55
 **/
@RestController
@RequestMapping("receiver")
public class ReceiverController {

    @Autowired
    @Qualifier("taskQueue")
    private DataQueue taskQueue;

    @PostMapping("send")
    public Result send(@RequestBody String data) throws InterruptedException {
        HashMap<String, Object> map = JSONObject.parseObject(data, HashMap.class);
        DataWrapper dataWrapper = DataWrapper.builder().sessionId(map.get("session_id").toString()).data(map).build();
        taskQueue.put(dataWrapper);
        return Result.success();
    }
}
