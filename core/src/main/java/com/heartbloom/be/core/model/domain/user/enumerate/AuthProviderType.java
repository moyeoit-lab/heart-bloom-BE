package com.heartbloom.be.core.model.domain.user.enumerate;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AuthProviderType {

    KAKAO("kakao", "카카오");

    private final String serializedValue;
    private final String description;

    AuthProviderType(String serializedValue,
                     String description) {
        this.serializedValue = serializedValue;
        this.description = description;
    }

    public static AuthProviderType findByCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown AuthProviderType: " + code));
    }

}