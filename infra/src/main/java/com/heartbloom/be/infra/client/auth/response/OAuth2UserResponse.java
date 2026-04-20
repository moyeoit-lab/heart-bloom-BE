package com.heartbloom.be.infra.client.auth.response;

public record OAuth2UserResponse (
        String name,
        String email
) {}
