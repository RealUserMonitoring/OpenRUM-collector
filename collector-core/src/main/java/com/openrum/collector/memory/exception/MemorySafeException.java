package com.openrum.collector.memory.exception;

/**
 * @description: MemorySafeException
 * @author: lou renzheng
 * @create: 2022-12-30 11:28
 **/
public class MemorySafeException extends Exception{
    public MemorySafeException() {
        super("not enough memory");
    }

    public MemorySafeException(String message) {
        super(message);
    }
}
