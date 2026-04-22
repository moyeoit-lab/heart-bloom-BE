package com.heartbloom.be.common.exception.code;

import com.heartbloom.be.common.enumerate.ApiHttpStatus;

public interface ErrorCode {

    String getCode();

    String getMessage();

    ApiHttpStatus getApiHttpStatus();

}
