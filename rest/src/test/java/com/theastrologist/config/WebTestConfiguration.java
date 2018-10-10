package com.theastrologist.config;

import com.theastrologist.service.ThemeService;
import com.theastrologist.util.TimeService;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;

@Configuration
@Profile("test")
public class WebTestConfiguration {
	@Bean
	@Primary
	public TimeService timeService() {
		return Mockito.spy(TimeService.class);
	}

	@Bean
	@Primary
	public ThemeService themeCalculator() {
		return new ThemeService();
	}


}
