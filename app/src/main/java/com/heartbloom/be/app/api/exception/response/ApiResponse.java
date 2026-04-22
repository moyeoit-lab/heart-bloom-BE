package com.heartbloom.be.app.api.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.heartbloom.be.common.enumerate.ApiStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T> (
        ApiStatus status,
        String message,
        T data
) {

    public ApiResponse(ApiStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(ApiStatus.SUCCESS, message, data);

    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ApiStatus.SUCCESS, null, data);

    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(ApiStatus.FAIL, message, null);
    }

}