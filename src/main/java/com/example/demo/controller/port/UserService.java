package com.example.demo.controller.port;

import com.example.demo.model.dto.UserCreateDto;
import com.example.demo.model.dto.UserUpdateDto;
import com.example.demo.repository.UserEntity;

public interface UserService {
    UserEntity getByEmail(String email);
    UserEntity getById(long id);
    UserEntity create(UserCreateDto userCreateDto);
    UserEntity update(long id, UserUpdateDto userUpdateDto);
    void login(long id);
    void verifyEmail(long id, String certificationCode);
}
