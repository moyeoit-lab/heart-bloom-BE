package com.heartbloom.be.core.repository.domain.user;

import com.heartbloom.be.core.model.domain.user.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long userId);

    Optional<User> findByEmail(String email);

}
