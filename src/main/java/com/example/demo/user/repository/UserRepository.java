package com.example.demo.user.repository;

import com.example.demo.user.constant.UserStatus;
import com.example.demo.user.repository.model.User;

public interface UserRepository {

    User findByIdAndStatus(long id, UserStatus userStatus);

    User findByEmailAndStatus(String email, UserStatus userStatus);

    User findById(long id);

    void save(User user);
}
