package com.heartbloom.be.common.exception;

import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {

    NOT_FOUND("NOT_FOUND", "회원 정보를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST)
    ;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    UserErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return "";
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return null;
    }
}
