package com.example.todoapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // vueとの開発用に、3000からのアクセスを許可する
        registry.addMapping("/tasks/**")
                .allowedMethods("*")
                .allowedOrigins("http://localhost:3000");
    }
}
