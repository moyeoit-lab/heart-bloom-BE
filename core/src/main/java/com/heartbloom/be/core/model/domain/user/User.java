package com.heartbloom.be.core.model.domain.user;

import com.heartbloom.be.core.model.domain.user.enumerate.AuthProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Long id;
    private String name;
    private String email;
    private AuthProviderType providerType;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static User create(String name,
                              String email,
                              AuthProviderType providerType,
                              LocalDateTime now) {
        return new User(
                null,
                name,
                email,
                providerType,
                now,
                now
        );
    }

}