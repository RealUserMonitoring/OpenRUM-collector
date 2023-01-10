package com.openrum.collector.queue;

import com.openrum.collector.exporter.DataWrapper;

import java.util.List;

/**
 * @author kyq
 * @date 2023/1/10 14:10
 * @description
 */
public class AbstractDataQueue<T> implements DataQueue<T>{
    @Override
    public void put(T data) throws InterruptedException {
    }

    @Override
    public T poll() {
        return null;
    }

    @Override
    public int drainTo(List<DataWrapper> c) {
        return 0;
    }

    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return false;
    }
}
