package com.heartbloom.be.app.api.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T> (
        boolean success,
        String message,
        T data
) {

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);

    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, null, data);

    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, message, null);
    }

}