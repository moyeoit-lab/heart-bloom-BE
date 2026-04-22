package com.heartbloom.be.app.security.access;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticateUser implements AccessUser {

    private final Long id;
    private final String name;
    private final String email;
    private final boolean deleted;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

}