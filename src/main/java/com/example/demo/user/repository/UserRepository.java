package com.example.demo.user.repository;

import com.example.demo.user.model.UserStatus;
import com.example.demo.user.service.model.User;

import java.util.Optional;

public interface UserRepository {

    User findByIdAndStatus(long id, UserStatus userStatus);

    User findByEmailAndStatus(String email, UserStatus userStatus);

    User findById(long id);

    void save(User user);
}
