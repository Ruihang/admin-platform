package com.louis.mongo.backup.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST","GET","DELETE","PUT","OPTIONS")
                .maxAge(168000)
                .allowedHeaders("*")
                .allowCredentials(true);//是否发送cookie
    }
}
