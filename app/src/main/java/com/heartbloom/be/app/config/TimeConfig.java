package com.heartbloom.be.app.config;

import com.heartbloom.be.common.time.FixedTimeProvider;
import com.heartbloom.be.common.time.GeneralTimeProvider;
import com.heartbloom.be.common.time.TimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Configuration
public class TimeConfig {

    @Bean
    @Profile({"local", "dev", "prod"})
    public TimeProvider generalTimeProvider() {
        return new GeneralTimeProvider();
    }

    @Bean
    @Profile("test")
    public TimeProvider fixedTimeProvider() {
        return new FixedTimeProvider(LocalDateTime.of(2026, 1, 1, 0, 0, 0));
    }

}
