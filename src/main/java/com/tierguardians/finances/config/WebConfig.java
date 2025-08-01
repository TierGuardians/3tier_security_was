package com.tierguardians.finances.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://192.168.56.1:3000",
                        //"http://192.168.1.23",
                        "http://3tier.prod",
                        "http://192.168.0.55:3000",
                        "http://frontend.local",
                        "http://api.frontend.local"
                        //"http://frontend.prod"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}