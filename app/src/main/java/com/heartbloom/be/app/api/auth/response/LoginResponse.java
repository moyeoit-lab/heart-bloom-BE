package com.heartbloom.be.app.api.auth.response;

import com.heartbloom.be.app.service.auth.dto.TokenResult;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 응답")
public record LoginResponse (
        @Schema(description = "서비스 access token")
        String accessToken
) {

    public static LoginResponse of(TokenResult tokenResult) {
        return new LoginResponse(tokenResult.accessToken());
    }

}
