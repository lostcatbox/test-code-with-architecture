package com.example.demo.domain.user.service;

import com.example.demo.domain.user.dto.req.UserCreateRequestDTO;
import com.example.demo.domain.user.dto.req.UserUpdateRequestDTO;
import com.example.demo.domain.user.entity.model.User;

public interface UserService {
    User getByEmail(String email);

    User getById(long id);

    User create(UserCreateRequestDTO userCreateRequestDto);

    User update(long id, UserUpdateRequestDTO userUpdateRequestDto);

    void login(long id);

    void verifyEmail(long id, String certificationCode);
}
