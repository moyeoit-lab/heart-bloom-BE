package com.heartbloom.be.app.application.auth.implementation.factory;

import com.heartbloom.be.common.exception.ServiceException;
import com.heartbloom.be.common.exception.code.AuthErrorCode;
import com.heartbloom.be.infra.client.auth.OAuth2Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OAuth2ClientFactory {

    private final List<OAuth2Client> oAuth2Clients;

    public OAuth2Client getOAuth2Client(String provider) {
        return oAuth2Clients.stream()
                .filter(client -> Objects.equals(provider, client.getProviderType().getSerializedValue()))
                .findFirst()
                .orElseThrow(() -> new ServiceException(AuthErrorCode.NOT_SUPPORT_PROVIDER));
    }

}
