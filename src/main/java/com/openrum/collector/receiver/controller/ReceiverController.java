package com.openrum.collector.receiver.controller;

import com.alibaba.fastjson.JSONObject;
import com.openrum.collector.common.domain.Result;
import com.openrum.collector.exporter.DataWrapper;
import com.openrum.collector.queue.DataQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: receiver controller
 * @author: lou renzheng
 * @create: 2022-12-30 10:55
 **/
@Slf4j
@RestController
@RequestMapping("receiver")
public class ReceiverController {

    @Autowired
    @Qualifier("taskQueue")
    private DataQueue taskQueue;

    private static AtomicInteger atomicInteger = new AtomicInteger();

    @PostMapping("send")
    public Result send(@RequestBody String data) throws InterruptedException {
        if (log.isDebugEnabled()){
            log.debug("Receiver data count:{}",atomicInteger.incrementAndGet());
        }
        HashMap<String, Object> map = JSONObject.parseObject(data, HashMap.class);
        DataWrapper dataWrapper = DataWrapper.builder().sessionId(map.get("session_id").toString()).data(map).build();
        taskQueue.put(dataWrapper);
        return Result.success();
    }
}
