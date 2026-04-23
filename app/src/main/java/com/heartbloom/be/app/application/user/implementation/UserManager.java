package com.heartbloom.be.app.application.user.implementation;

import com.heartbloom.be.common.time.TimeProvider;
import com.heartbloom.be.core.model.domain.user.User;
import com.heartbloom.be.core.model.domain.user.enumerate.AuthProviderType;
import com.heartbloom.be.core.repository.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserManager {

    private final TimeProvider timeProvider;

    private final UserRepository userRepository;

    /* 유저 생성 */
    public User createUser(String name, String email, AuthProviderType providerType) {
        LocalDateTime now = timeProvider.now();
        User newUser = User.create(name, email, providerType, now);
        return userRepository.save(newUser);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

}
