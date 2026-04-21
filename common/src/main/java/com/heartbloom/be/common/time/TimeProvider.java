package com.heartbloom.be.common.time;

import java.time.Instant;
import java.time.LocalDateTime;

public interface TimeProvider {

    LocalDateTime now();

    Instant nowInstant();

}
