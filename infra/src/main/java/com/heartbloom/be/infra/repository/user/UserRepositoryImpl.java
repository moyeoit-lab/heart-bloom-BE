package com.heartbloom.be.infra.repository.user;

import com.heartbloom.be.core.model.domain.user.User;
import com.heartbloom.be.core.repository.domain.user.UserRepository;
import com.heartbloom.be.infra.dao.jpa.user.UserJpaDao;
import com.heartbloom.be.infra.entity.converter.UserConverter;
import com.heartbloom.be.infra.entity.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaDao userJpaDao;

    @Override
    public User save(User user) {
        UserEntity savedUserEntity = userJpaDao.save(UserConverter.toEntity(user));
        return UserConverter.toModel(savedUserEntity);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaDao.findById(userId)
                .map(UserConverter::toModel);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaDao.findByEmail(email)
                .map(UserConverter::toModel);
    }
}