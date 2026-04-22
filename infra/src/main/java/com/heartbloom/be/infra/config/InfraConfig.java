package com.heartbloom.be.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableJpaRepositories(basePackages = {"com.heartbloom.be.infra.dao"})
@EntityScan(basePackages = {"com.heartbloom.be.infra.entity"})
@EnableJpaAuditing
public class InfraConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
