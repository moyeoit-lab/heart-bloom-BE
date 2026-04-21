package com.heartbloom.be.app.api.auth.response;

import com.heartbloom.be.app.application.auth.dto.TokenResult;

public record LoginResponse (
        String accessToken
) {

    public static LoginResponse of(TokenResult tokenResult) {
        return new LoginResponse(tokenResult.accessToken());
    }

}
