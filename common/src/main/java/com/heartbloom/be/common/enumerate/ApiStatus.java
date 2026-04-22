package com.heartbloom.be.common.enumerate;

public enum ApiStatus {

    SUCCESS(true),
    FAIL(false);

    private final boolean status;

    ApiStatus(boolean status) {
        this.status = status;
    }

}
