package com.heartbloom.be.app.api.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "OAuth 로그인 URL 응답")
public record GetLoginUrlResponse (
        @Schema(description = "OAuth 로그인 URL")
        String loginUrl,

        @Schema(description = "OAuth 제공자 타입", example = "kakao")
        String providerType
) {

    public static GetLoginUrlResponse of(String loginUrl, String providerType) {
        return new GetLoginUrlResponse(loginUrl, providerType);
    }

}
