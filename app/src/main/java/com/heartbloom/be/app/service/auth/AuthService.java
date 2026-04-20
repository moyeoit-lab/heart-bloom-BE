package com.heartbloom.be.app.service.auth;

import com.heartbloom.be.infra.client.auth.OAuth2Client;
import com.heartbloom.be.infra.client.auth.response.OAuth2TokenResponse;
import com.heartbloom.be.infra.client.auth.response.OAuth2UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final List<OAuth2Client> oAuth2Clients;

    public String getLoginUrl(String redirectUri, String state, String provider) {
        // TODO : common 모듈 추가 후 예외 처리
        OAuth2Client oAuth2Client = getAvailableOAuth2Client(provider);

        return oAuth2Client.getLoginUrl(redirectUri, state);
    }

    public void login(String code, String redirectUri, String provider) {
        OAuth2Client oAuth2Client = getAvailableOAuth2Client(provider);
        OAuth2TokenResponse token = oAuth2Client.getToken(code, redirectUri);
        OAuth2UserResponse userResponse = oAuth2Client.getUserInfo(token.getAccessToken());
        // TODO : JWT 생성
    }

    private OAuth2Client getAvailableOAuth2Client(String provider) {
        return oAuth2Clients.stream()
                .filter(client -> Objects.equals(provider, client.getProviderType().getSerializedValue()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 인증 정보 제공자 (provider) 입니다."));
    }

}