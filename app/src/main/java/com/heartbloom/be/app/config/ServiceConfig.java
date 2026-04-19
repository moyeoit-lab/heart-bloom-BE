package com.heartbloom.be.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.heartbloom.be.infra"})
public class ServiceConfig {
}
