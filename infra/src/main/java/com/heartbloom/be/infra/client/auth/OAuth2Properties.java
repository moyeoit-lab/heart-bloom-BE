package com.heartbloom.be.infra.client.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OAuth2Properties {

    private Auth auth;
    private Api api;

    @Getter
    @Setter
    public static class Auth {
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private List<String> scope;
    }

    @Getter
    @Setter
    public static class Api {
        private String authorizationUri;
        private String tokenUri;
        private String userInfoUri;
    }

}