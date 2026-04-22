package com.heartbloom.be.common.enumerate;

import lombok.Getter;

@Getter
public enum ApiHttpStatus {

    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    UNSUPPORTED_MEDIA_TYPE(415),
    INTERNAL_SERVER_ERROR(500)
    ;

    private final Integer code;

    ApiHttpStatus(Integer code) {
        this.code = code;
    }

}