package com.openrum.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
//@EnableDiscoveryClient
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication
                .run(StartApplication.class, args);
    }

}
