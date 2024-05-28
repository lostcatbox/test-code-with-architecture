package com.example.demo.domain.user.repository.impl;

import com.example.demo.domain.user.entity.UserEntity;
import com.example.demo.domain.user.entity.model.User;
import com.example.demo.domain.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User findByIdAndStatus(long id, UserStatus userStatus) {
        return UserEntity.from(userJpaRepository.findByIdAndStatus(id, userStatus).orElseThrow().to()).to();
    }

    @Override
    public User findByEmailAndStatus(String email, UserStatus userStatus) {
        return UserEntity.from(userJpaRepository.findByEmailAndStatus(email, userStatus).orElseThrow().to()).to();
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(UserEntity.from(user)).to();
    }
}
