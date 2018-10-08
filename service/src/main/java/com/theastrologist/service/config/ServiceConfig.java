package com.theastrologist.service.config;


import com.theastrologist.service.ThemeCalculator;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class ServiceConfig {

    @Bean
    public ThemeCalculator themeCalculator() {
        return new ThemeCalculator();
    }
}
