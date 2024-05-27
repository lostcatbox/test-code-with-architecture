package com.example.demo.user.service;

import com.example.demo.user.controller.dto.request.UserCreateDto;
import com.example.demo.user.controller.dto.request.UserUpdateDto;
import com.example.demo.user.controller.port.UserService;
import com.example.demo.user.constant.UserStatus;
import com.example.demo.user.repository.UserRepository;

import com.example.demo.user.repository.model.User;
import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UUIDHolder;
import com.example.demo.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UUIDHolder uuidHolder;
    private final ClockHolder clockHolder;
    private final MailSender mailSender;

    @Override
    public User getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE);
    }

    @Override
    public User getByEmail(String email) {
        User user = userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE);
        return user;
    }

    @Transactional
    public User updateUser(long id, UserUpdateDto userUpdateDto){
        User user = userRepository.findById(id);
        user.updateUser(userUpdateDto, uuidHolder);
        userRepository.save(user);
        return userRepository.findById(id);
    }

    @Override
    public User getByIdOrElseThrow(long id) {

        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE);
    }
    @Transactional
    public void login(long id) {
        User user= userRepository.findById(id);
        user.login(clockHolder);
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
    @Transactional
    public User createUser(UserCreateDto userCreateDto) {
        User user = User.from(userCreateDto, uuidHolder);
        userRepository.save(user);
        mailSender.sendCertificationEmail(user.getEmail(), user.getId(),user.getCertificationCode());
        return null;
    }
}