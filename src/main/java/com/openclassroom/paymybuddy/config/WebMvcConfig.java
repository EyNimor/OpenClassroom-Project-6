package com.openclassroom.paymybuddy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.openclassroom.paymybuddy")
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("**")
        .allowedMethods("*")
        .allowedHeaders("*")
        .allowedOrigins("*")
        .allowCredentials(false);
    }
    
}