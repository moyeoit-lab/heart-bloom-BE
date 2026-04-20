package com.heartbloom.be.app.api.auth.response;

public record GetLoginUrlResponse (
        String loginUrl,
        String providerType
) {

    public static GetLoginUrlResponse of(String loginUrl, String providerType) {
        return new GetLoginUrlResponse(loginUrl, providerType);
    }

}
