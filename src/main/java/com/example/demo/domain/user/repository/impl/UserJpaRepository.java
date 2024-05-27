package com.example.demo.domain.user.repository.impl;

import com.example.demo.domain.user.enums.UserStatus;

import java.util.Optional;

import com.example.demo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);
}
