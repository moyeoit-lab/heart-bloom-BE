package com.heartbloom.be.infra.entity.converter;

import com.heartbloom.be.core.model.domain.user.User;
import com.heartbloom.be.core.model.domain.user.enumerate.AuthProviderType;
import com.heartbloom.be.infra.entity.domain.user.UserEntity;

public class UserConverter {

    public static UserEntity toEntity(User model) {
        return new UserEntity(
                model.getId(),
                model.getName(),
                model.getEmail(),
                model.getProviderType().name()
        );
    }

    public static User toModel(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                AuthProviderType.findByCode(entity.getProviderType()),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

}
