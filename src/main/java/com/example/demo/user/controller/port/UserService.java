package com.example.demo.user.controller.port;

import com.example.demo.user.controller.dto.request.UserCreateDto;
import com.example.demo.user.controller.dto.request.UserUpdateDto;
import com.example.demo.user.repository.entity.UserEntity;
import com.example.demo.user.service.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserService {
    User getById(long id);

    User getByEmail(String email);

    User getByIdOrElseThrow(long id);

    @Transactional
    User createUser(UserCreateDto userCreateDto);

    @Transactional
    User updateUser(long id, UserUpdateDto userUpdateDto);

    @Transactional
    void login(long id);

    @Transactional
    void verifyEmail(long id, String certificationCode);

}
