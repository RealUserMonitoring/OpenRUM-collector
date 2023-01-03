package com.openrum.collector.receiver.controller;

import com.openrum.collector.common.domain.Result;
import com.openrum.collector.queue.DataQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private DataQueue processorDataQueue;

    @PostMapping("send")
    public Result send(@RequestBody String data) throws InterruptedException {

        processorDataQueue.put(data);
        
        return Result.success();
    }
}
