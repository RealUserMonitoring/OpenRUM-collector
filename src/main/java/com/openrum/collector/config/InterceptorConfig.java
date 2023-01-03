package com.openrum.collector.config;

import com.openrum.collector.authorization.config.JWTInterceptor;
import com.openrum.collector.memory.interceptor.MemorySafeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @description: IntercaptorConfig
 * @author: lou renzheng
 * @create: 2022-12-29
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private JWTInterceptor jwtInterceptor;

    @Resource
    private MemorySafeInterceptor memorySafeInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                //拦截的路径
                .addPathPatterns("/**")
                //排除登录接口
                .excludePathPatterns("/token/getToken");
        registry.addInterceptor(memorySafeInterceptor)
                //拦截的路径
                .addPathPatterns("/**")
                //排除登录接口
                .excludePathPatterns("/token/getToken");
    }
}
