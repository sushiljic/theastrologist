package com.theastrologist.config;

import com.theastrologist.util.TimeService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestConfiguration {
	@Bean
	@Primary
	public TimeService timeService() {
		return Mockito.spy(TimeService.class);
	}
}
