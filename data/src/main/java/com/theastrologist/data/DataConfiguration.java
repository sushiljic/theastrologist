package com.theastrologist.data;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EntityScan(basePackages = {"com.theastrologist.domain"})
//@EnableJpaRepositories
//@EnableAutoConfiguration
//@EnableTransactionManagement
public class DataConfiguration {

}
