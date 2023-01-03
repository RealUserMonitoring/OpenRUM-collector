package com.openrum.collector.memory.interceptor;

import com.openrum.collector.memory.domain.MemoryInfo;
import com.openrum.collector.memory.exception.MemorySafeException;
import com.openrum.collector.utils.SpringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: MemorySafeInterceptor
 * @author: lou renzheng
 * @create: 2022-12-29
 **/
public class MemorySafeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws MemorySafeException {
        MemoryInfo memoryInfo = SpringUtils.getBean(MemoryInfo.class);

        if(memoryInfo.enoughMemory()){
           return true;
        }
        System.gc();
        System.runFinalization();

        if(!memoryInfo.enoughMemory()){
            throw new MemorySafeException();
        }
        return true;
    }
}
