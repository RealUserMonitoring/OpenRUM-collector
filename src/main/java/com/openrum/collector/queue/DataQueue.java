package com.openrum.collector.queue;

/**
 * @description: data queue
 * @author: lou renzheng
 * @create: 2022-12-30
 **/
public interface DataQueue<T> {

    void put(T data) throws InterruptedException;

    T poll();

    int size();
}
