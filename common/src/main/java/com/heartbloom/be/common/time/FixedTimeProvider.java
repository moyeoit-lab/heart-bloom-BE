package com.heartbloom.be.common.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FixedTimeProvider implements TimeProvider {

    private LocalDateTime fixedTime;

    public FixedTimeProvider(LocalDateTime fixedTime) {
        this.fixedTime = fixedTime;
    }

    public void setFixedTime(LocalDateTime fixedTime) {
        this.fixedTime = fixedTime;
    }

    @Override
    public LocalDateTime now() {
        return fixedTime;
    }

    @Override
    public Instant nowInstant() {
        return fixedTime.toInstant(ZoneOffset.UTC);
    }

}
