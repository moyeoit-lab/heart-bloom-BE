package com.heartbloom.be.app.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserPrincipal implements UserDetails {

    private final Long id;
    private final String name;
    private final String email;
    private final boolean deleted;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserPrincipal(Long id,
                               String name,
                               String email,
                               boolean deleted,
                               Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deleted = deleted;
        this.authorities = authorities;
    }




    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !deleted;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return name;
    }
}
