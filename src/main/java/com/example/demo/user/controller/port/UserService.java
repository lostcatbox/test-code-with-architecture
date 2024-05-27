package com.example.demo.user.controller.port;

import com.example.demo.user.controller.dto.request.UserCreateDto;
import com.example.demo.user.controller.dto.request.UserUpdateDto;
import com.example.demo.user.repository.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    User getById(long id);

    User getByEmail(String email);

    User getByIdOrElseThrow(long id);

    User createUser(UserCreateDto userCreateDto);

    User updateUser(long id, UserUpdateDto userUpdateDto);

    void login(long id);

    void verifyEmail(long id, String certificationCode);

}
