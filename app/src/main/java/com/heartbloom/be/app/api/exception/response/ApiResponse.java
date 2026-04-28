package com.heartbloom.be.app.api.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "공통 API 응답")
public record ApiResponse<T> (
        @Schema(description = "요청 성공 여부", example = "true")
        boolean success,

        @Schema(description = "응답 메시지")
        String message,

        @Schema(description = "응답 데이터")
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
