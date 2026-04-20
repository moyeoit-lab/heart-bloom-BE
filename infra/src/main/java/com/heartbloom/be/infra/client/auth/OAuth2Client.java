package com.heartbloom.be.infra.client.auth;

import com.heartbloom.be.core.model.domain.user.enumerate.AuthProviderType;
import com.heartbloom.be.infra.client.auth.response.OAuth2TokenResponse;
import com.heartbloom.be.infra.client.auth.response.OAuth2UserResponse;

public interface OAuth2Client {

    AuthProviderType getProviderType();

    String getLoginUrl(String redirectUri, String state);

    OAuth2TokenResponse getToken(String code, String redirectUri);

    OAuth2UserResponse getUserInfo(String token);

}