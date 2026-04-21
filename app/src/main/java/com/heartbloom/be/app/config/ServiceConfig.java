package com.heartbloom.be.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.heartbloom.be.infra", "com.heartbloom.be.common"})
public class ServiceConfig {
}
