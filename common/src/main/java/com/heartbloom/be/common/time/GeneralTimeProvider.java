package com.heartbloom.be.common.time;

import java.time.Instant;
import java.time.LocalDateTime;

public class GeneralTimeProvider implements TimeProvider {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    @Override
    public Instant nowInstant() {
        return Instant.now();
    }

}
