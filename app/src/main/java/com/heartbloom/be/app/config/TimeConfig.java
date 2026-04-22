package com.heartbloom.be.app.config;

import com.heartbloom.be.common.time.GeneralTimeProvider;
import com.heartbloom.be.common.time.TimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TimeConfig {

    @Bean
    @Profile({"local", "dev", "prod"})
    public TimeProvider generalTimeProvider() {
        return new GeneralTimeProvider();
    }

}
