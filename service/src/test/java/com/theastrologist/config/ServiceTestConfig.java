package com.theastrologist.config;

import com.theastrologist.service.ThemeCalculator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServiceTestConfig {
    @Bean
    public ThemeCalculator themeCalculator() {
        return new ThemeCalculator();
    }
}
