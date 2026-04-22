package com.heartbloom.be.app.api.exception;

import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.common.enumerate.ApiHttpStatus;
import com.heartbloom.be.common.exception.code.ErrorCode;
import com.heartbloom.be.common.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiResponse<Void>> processServiceException(ServiceException e) {
        ErrorCode code = e.getErrorCode();
        ApiHttpStatus apiHttpStatus = code.getApiHttpStatus();
        HttpStatus springHttpStatus = HttpStatus.valueOf(apiHttpStatus.getCode());
        return ResponseEntity
                .status(springHttpStatus)
                .body(ApiResponse.fail(code.getMessage()));
    }

}
