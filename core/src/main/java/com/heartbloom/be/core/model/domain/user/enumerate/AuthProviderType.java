package com.heartbloom.be.core.model.domain.user.enumerate;

import lombok.Getter;

@Getter
public enum AuthProviderType {

    KAKAO("카카오");

    private final String description;

    AuthProviderType(String description) {
        this.description = description;
    }

}