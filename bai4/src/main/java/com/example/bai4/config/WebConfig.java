package com.example.bai4.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve images saved during runtime from project folder so templates can access them
        String imagesPath = "file:src/main/resources/static/images/";
        registry.addResourceHandler("/images/**")
                .addResourceLocations(imagesPath);
    }
}
