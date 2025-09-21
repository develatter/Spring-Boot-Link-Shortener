package com.develatter.linkshortener.infraestructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Configuration class to set up CORS (Cross-Origin Resource Sharing) settings for the application.
 * This configuration allows specified origins to access the resources of the application
 * with defined HTTP methods and headers.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    public static final String[] ALLOWED_ORIGINS = {
            "http://localhost:5173",
            "http://localhost:8080",
            "https://www.develatter.com",
    };
    public static final String[] ALLOWED_METHODS = {
            "GET",
            "POST",
            "PUT",
            "DELETE",
            "OPTIONS"
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(ALLOWED_ORIGINS)
                .allowedMethods(ALLOWED_METHODS)
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }

}