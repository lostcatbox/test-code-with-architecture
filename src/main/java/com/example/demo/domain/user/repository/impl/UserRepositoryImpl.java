package com.example.demo.domain.user.repository.impl;

import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User findByIdAndStatus(long id, UserStatus userStatus) {
        return userJpaRepository.findByIdAndStatus(id,userStatus).orElseThrow();
    }

    @Override
    public User findByEmailAndStatus(String email, UserStatus userStatus) {
        return userJpaRepository.findByEmailAndStatus(email,userStatus).orElseThrow();
    }
}
