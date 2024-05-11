package com.example.demo.user.service;

import com.example.demo.common.domain.ResourceNotFoundException;
import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UuidHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserUpdate;

import java.time.Clock;

import com.example.demo.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Builder
public class UserService {

    private final UserRepository userRepository;
    private final CertificationService certificationService;
    private final ClockHolder clockHolder;
    private final UuidHolder uuidHolder;

    public User getByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
            .orElseThrow(() -> new ResourceNotFoundException("Users", email));
    }

    public User getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
            .orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Transactional
    public User create(UserCreate userCreate) {
        User user = User.from(userCreate, uuidHolder);
        User savedUser = userRepository.save(user);
        certificationService.send(userCreate.getEmail(), savedUser.getId(), savedUser.getCertificationCode());
        return savedUser;
    }

    @Transactional
    public User update(long id, UserUpdate userUpdate) {
        User user = getById(id);
        User updatedUser = user.update(userUpdate, clockHolder);
        User savedUser = userRepository.save(updatedUser);
        return savedUser;
    }

    @Transactional
    public void login(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
        User loginedUser = user.login(clockHolder);
        userRepository.save(loginedUser);
    }

    @Transactional
    public void verifyEmail(long id, String certificationCode) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
        User verifiedUser = user.verifyEmail(certificationCode);
        userRepository.save(verifiedUser);
    }
}