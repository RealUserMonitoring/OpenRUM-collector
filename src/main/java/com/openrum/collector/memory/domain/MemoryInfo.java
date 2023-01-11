package com.openrum.collector.memory.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: MemoryInfo
 * @author: lou renzheng
 * @create: 2022-12-29
 **/
@Component
@ConfigurationProperties(prefix = "memory")
@Data
public class MemoryInfo {

    private Long maxFreeMemory;

    /**
    * @Description: validate free memory
    * @Param: []
    * @return: boolean
    */
    public boolean enoughMemory(){
        long maxMemory = Runtime.getRuntime().maxMemory()/1024/1024;
        long totalMemory = Runtime.getRuntime().totalMemory()/1024/1024;
        return maxMemory-totalMemory>this.maxFreeMemory;
    }
}
