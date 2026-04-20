package com.heartbloom.be.infra.client.auth.implement.kakao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartbloom.be.core.model.domain.user.enumerate.AuthProviderType;
import com.heartbloom.be.infra.client.auth.OAuth2Client;
import com.heartbloom.be.infra.client.auth.response.OAuth2TokenResponse;
import com.heartbloom.be.infra.client.auth.response.OAuth2UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoAuthClient implements OAuth2Client {

    private final KakaoOAuth2Properties properties;
    private final WebClient webClient;
    private final ObjectMapper mapper;

    private final String CLIENT_ID_KEY = "client_id";
    private final String CLIENT_SECRET_KEY = "client_secret";
    private final String CODE_KEY = "code";
    private final String GRANT_TYPE_KEY = "grant_type";
    private final String GRANT_TYPE_VALUE = "authorization_code";
    private final String REDIRECT_URI_KEY = "redirect_uri";

    @Override
    public AuthProviderType getProviderType() {
        return AuthProviderType.KAKAO;
    }

    @Override
    public String getLoginUrl(String redirectUri, String state) {
        return UriComponentsBuilder
                .fromUriString(properties.getApi().getAuthorizationUri())
                .queryParam("client_id", properties.getAuth().getClientId())
                .queryParam("redirect_uri", redirectUri != null ? redirectUri : properties.getAuth().getRedirectUri())
                .queryParam("response_type", "code")
                .queryParam("scope", String.join(" ", properties.getAuth().getScope()))
                .queryParam("state", "state")
                .build()
                .toUriString();
    }

    @Override
    public OAuth2TokenResponse getToken(String code, String redirectUri) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(CLIENT_ID_KEY, properties.getAuth().getClientId());
        body.add(CLIENT_SECRET_KEY, properties.getAuth().getClientSecret());
        body.add(CODE_KEY, code);
        body.add(REDIRECT_URI_KEY, redirectUri);
        body.add(GRANT_TYPE_KEY, GRANT_TYPE_VALUE);

        return webClient.post()
                .uri(properties.getApi().getTokenUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(body))
                .retrieve()
                .bodyToMono(OAuth2TokenResponse.class)
                .block();
    }

    @Override
    public OAuth2UserResponse getUserInfo(String token) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

        String responseBody = webClient.get()
                .uri(properties.getApi().getUserInfoUri())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return extractUserInfo(responseBody);
    }

    private OAuth2UserResponse extractUserInfo(String responseBody) {
        try {
            JsonNode root = mapper.readTree(responseBody);
            JsonNode kakaoAccount = root.get("kakao_account");

            String email = (kakaoAccount != null && kakaoAccount.has("email"))
                    ? kakaoAccount.get("email").asText()
                    : null;

            String nickname = null;
            if (kakaoAccount != null && kakaoAccount.has("profile")) {
                JsonNode profile = kakaoAccount.get("profile");
                if (profile.has("nickname")) {
                    nickname = profile.get("nickname").asText();
                }
            }

            return new OAuth2UserResponse(nickname, email);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
