package com.openrum.collector.config;

import com.openrum.collector.authorization.config.JWTInterceptor;
import com.openrum.collector.memory.interceptor.MemorySafeInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: IntercaptorConfig
 * @author: lou renzheng
 * @create: 2022-12-29
 **/
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                //拦截的路径
                .addPathPatterns("/**")
                //排除登录接口
                .excludePathPatterns("/token/getToken");
        registry.addInterceptor(new MemorySafeInterceptor())
                //拦截的路径
                .addPathPatterns("/**")
                //排除登录接口
                .excludePathPatterns("/token/getToken");
    }
}
