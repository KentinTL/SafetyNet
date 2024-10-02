package com.openclassrooms.safetynet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.openclassrooms.safetynet.exceptions.RequestResponseInterceptor;

@Configuration
public class FilterConfig {
    @Bean
    public RequestResponseInterceptor loggingFilter() {
        return new RequestResponseInterceptor();
    }
}