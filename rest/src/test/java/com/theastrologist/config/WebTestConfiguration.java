package com.theastrologist.config;

import com.theastrologist.service.ThemeCalculator;
import com.theastrologist.service.config.ServiceConfig;
import com.theastrologist.util.TimeService;
import com.theastrologist.util.TimeServiceTest;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;

@TestConfiguration
@Profile("test")
public class WebTestConfiguration {
	@Bean
	@Primary
	public TimeService timeService() {
		return Mockito.spy(TimeService.class);
	}

	@Bean
	public ThemeCalculator themeCalculator() {
		return new ThemeCalculator();
	}


}
