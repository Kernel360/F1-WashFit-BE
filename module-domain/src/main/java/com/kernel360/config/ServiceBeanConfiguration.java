package com.kernel360.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.kernel360.infra") //하위모듈 컴포넌트 스캔
public class ServiceBeanConfiguration {
}
