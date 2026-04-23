package com.heartbloom.be.common.exception.code;

import com.heartbloom.be.common.enumerate.ApiHttpStatus;

public enum BouquetErrorCode implements ErrorCode {

    NOT_FOUND("NOT_FOUND", "꽃다발 정보를 찾을 수 없습니다.", ApiHttpStatus.BAD_REQUEST),
    LINK_NOT_FOUND("LINK_NOT_FOUND", "부케 링크를 찾을 수 없습니다.", ApiHttpStatus.NOT_FOUND),
    TYPE_NOT_FOUND("TYPE_NOT_FOUND", "부케 타입을 찾을 수 없습니다.", ApiHttpStatus.NOT_FOUND),
    RECEIVER_NOT_FOUND("RECEIVER_NOT_FOUND", "부케 수신자를 찾을 수 없습니다.", ApiHttpStatus.NOT_FOUND),
    LINK_EXPIRED("LINK_EXPIRED", "만료된 부케 링크입니다.", ApiHttpStatus.BAD_REQUEST),
    LINK_ALREADY_COMPLETED("LINK_ALREADY_COMPLETED", "이미 답변이 완료된 부케 링크입니다.", ApiHttpStatus.BAD_REQUEST)
    ;

    private final String code;
    private final String message;
    private final ApiHttpStatus apiHttpStatus;

    BouquetErrorCode(String code, String message, ApiHttpStatus apiHttpStatus) {
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
