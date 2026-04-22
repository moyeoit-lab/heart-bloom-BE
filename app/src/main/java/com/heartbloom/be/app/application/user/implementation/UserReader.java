package com.heartbloom.be.app.application.user.implementation;

import com.heartbloom.be.common.exception.ServiceException;
import com.heartbloom.be.common.exception.code.UserErrorCode;
import com.heartbloom.be.core.model.domain.user.User;
import com.heartbloom.be.core.repository.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    public User read(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(UserErrorCode.NOT_FOUND));
    }

}