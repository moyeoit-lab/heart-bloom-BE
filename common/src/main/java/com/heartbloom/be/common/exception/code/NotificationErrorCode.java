package com.heartbloom.be.common.exception.code;

import com.heartbloom.be.common.enumerate.ApiHttpStatus;

public enum NotificationErrorCode implements ErrorCode {

    NOT_FOUND("NOTIFICATION_NOT_FOUND", "알림을 찾을 수 없습니다.", ApiHttpStatus.NOT_FOUND),
    ACCESS_DENIED("NOTIFICATION_ACCESS_DENIED", "해당 알림에 접근할 수 없습니다.", ApiHttpStatus.FORBIDDEN)
    ;

    private final String code;
    private final String message;
    private final ApiHttpStatus apiHttpStatus;

    NotificationErrorCode(String code, String message, ApiHttpStatus apiHttpStatus) {
        this.code = code;
        this.message = message;
        this.apiHttpStatus = apiHttpStatus;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public ApiHttpStatus getApiHttpStatus() {
        return this.apiHttpStatus;
    }

}
