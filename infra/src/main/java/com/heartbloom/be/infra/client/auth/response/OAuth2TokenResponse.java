package com.heartbloom.be.infra.client.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuth2TokenResponse {

    @JsonProperty("access_token")
    private String accessToken;
}
