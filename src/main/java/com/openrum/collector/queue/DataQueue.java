package com.openrum.collector.queue;

import java.util.Collection;

/**
 * @description: data queue
 * @author: lou renzheng
 * @create: 2022-12-30
 **/
public interface DataQueue<T> {

    void put(T data) throws InterruptedException;

    T poll();

    int size();

    int drainTo(Collection<? super T> c);

    boolean isEmpty();
}
