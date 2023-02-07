package com.openrum.collector.queue.impl;

import com.openrum.collector.queue.AbstractDataQueue;

/**
 * @author kyq
 * @date 2023/1/10 14:13
 * @description
 */
public class KafkaDataQueue<T> extends AbstractDataQueue<T> {

    @Override
    public void put(T data) throws InterruptedException {
        //TODO 生产消息,往kafka发送数据
    }
}
