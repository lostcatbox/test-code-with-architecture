package com.example.demo.user.service;

import com.example.demo.user.controller.dto.request.UserCreateDto;
import com.example.demo.user.controller.dto.request.UserUpdateDto;
import com.example.demo.user.controller.port.UserService;
import com.example.demo.user.model.UserStatus;
import com.example.demo.user.repository.UserRepository;

import java.time.Clock;

import com.example.demo.user.service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE);
    }

    @Override
    public User getByEmail(String email) {
        User user = userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE);
        return user;
    }

    public User updateUser(long id, UserUpdateDto userUpdateDto){
        User user = userRepository.findById(id);
        user.updateUser(userUpdateDto);
        userRepository.save(user);
        return userRepository.findById(id);
    }

    @Override
    public User getByIdOrElseThrow(long id) {

        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE);
    }
    public void login(long id) {
        User user= userRepository.findById(id);
        user.setLastLoginAt(Clock.systemUTC().millis());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void verifyEmail(long id, String certificationCode) {
        User user = userRepository.findById(id);
        user.checkCertificationCode(certificationCode);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    @Override
    public User createUser(UserCreateDto userCreateDto) {
        User user = userCreateDto.toUser();
        userRepository.save(user);
        return null;
    }
}