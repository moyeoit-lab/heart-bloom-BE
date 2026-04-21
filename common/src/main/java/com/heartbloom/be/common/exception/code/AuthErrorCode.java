package com.heartbloom.be.common.exception.code;

import com.heartbloom.be.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {

    NOT_SUPPORT_PROVIDER("NOT_SUPPORT_PROVIDER", "지원하지 않는 인증 제공자입니다.", HttpStatus.BAD_REQEUST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    AuthErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
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
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
