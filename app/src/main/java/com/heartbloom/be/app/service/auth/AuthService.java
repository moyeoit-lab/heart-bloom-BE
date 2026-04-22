package com.heartbloom.be.app.service.auth;

import com.heartbloom.be.app.application.auth.implementation.factory.OAuth2ClientFactory;
import com.heartbloom.be.app.service.auth.dto.TokenResult;
import com.heartbloom.be.app.security.jwt.JwtGenerator;
import com.heartbloom.be.app.application.user.implementation.UserManager;
import com.heartbloom.be.core.model.domain.user.User;
import com.heartbloom.be.core.model.domain.user.enumerate.AuthProviderType;
import com.heartbloom.be.core.repository.domain.user.UserRepository;
import com.heartbloom.be.infra.client.auth.OAuth2Client;
import com.heartbloom.be.infra.client.auth.response.OAuth2TokenResponse;
import com.heartbloom.be.infra.client.auth.response.OAuth2UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final OAuth2ClientFactory oAuth2ClientFactory;

    private final UserRepository userRepository;
    private final JwtGenerator jwtGenerator;
    private final UserManager userManager;

    public String getLoginUrl(String redirectUri, String state, String provider) {
        OAuth2Client oAuth2Client = oAuth2ClientFactory.getOAuth2Client(provider);
        return oAuth2Client.getLoginUrl(redirectUri, state);
    }

    public TokenResult login(String code, String redirectUri, String provider) {
        OAuth2Client oAuth2Client = oAuth2ClientFactory.getOAuth2Client(provider);
        OAuth2TokenResponse token = oAuth2Client.getToken(code, redirectUri);
        OAuth2UserResponse userResponse = oAuth2Client.getUserInfo(token.getAccessToken());

        AuthProviderType providerType = AuthProviderType.findByCode(provider);
        User user = userRepository.findByEmail(userResponse.email())
                .orElseGet(() -> userManager.createUser(userResponse.name(), userResponse.email(), providerType));

        return jwtGenerator.generateAccess(user.getId(), user.getEmail(), user.isDeleted());
    }

}