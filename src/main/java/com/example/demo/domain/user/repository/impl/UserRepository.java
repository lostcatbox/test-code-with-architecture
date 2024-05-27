package com.example.demo.domain.user.repository.impl;

import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.enums.UserStatus;

public interface UserRepository {
    User findByIdAndStatus(long id, UserStatus userStatus);
    User findByEmailAndStatus(String email, UserStatus userStatus);
}
