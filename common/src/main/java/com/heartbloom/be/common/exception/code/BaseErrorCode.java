package com.heartbloom.be.common.exception.code;

import com.heartbloom.be.common.enumerate.ApiHttpStatus;

public enum BaseErrorCode implements ErrorCode {

    NOT_FOUND("NOT_FOUND_BASE", "요청하신 데이터를 찾을 수 없습니다.", ApiHttpStatus.BAD_REQUEST),
    UNAUTHORIZED("UNAUTHORIZED", "해당 권한으로 이 작업을 처리할 수 없습니다.", ApiHttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final ApiHttpStatus apiHttpStatus;

    BaseErrorCode(String code, String message, ApiHttpStatus apiHttpStatus) {
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
