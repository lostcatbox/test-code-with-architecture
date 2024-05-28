package com.example.demo.domain.user.service.impl;

import com.example.demo.common.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.common.util.TimeUtil;
import com.example.demo.common.util.UUidCreate;
import com.example.demo.domain.user.dto.req.UserCreateRequestDTO;
import com.example.demo.domain.user.dto.req.UserUpdateRequestDTO;
import com.example.demo.domain.user.entity.UserEntity;
import com.example.demo.domain.user.entity.model.User;
import com.example.demo.domain.user.enums.UserStatus;
import com.example.demo.domain.user.repository.impl.UserJpaRepository;
import com.example.demo.domain.user.repository.impl.UserRepository;
import com.example.demo.domain.user.service.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Builder
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userJpaRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final UUidCreate uUidCreate;
    private final TimeUtil timeUtil;

    public User getByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE);
    }

    public User getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE);
    }

    @Transactional
    public User create(UserCreateRequestDTO userCreateRequestDto) {
        User user = User.createUser(userCreateRequestDto,uUidCreate);
        userRepository.save(user);
        String certificationUrl = generateCertificationUrl(user);
        sendCertificationEmail(user.email(), certificationUrl);
        return user;
    }

    @Transactional
    public User update(long id, UserUpdateRequestDTO userUpdateRequestDto) {
        User user = User.updateUser(id,userUpdateRequestDto);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void login(long id) {
       User user = User.updateLastLoginAt(id, timeUtil);
        userRepository.save(user);
    }

    @Transactional
    public void verifyEmail(long id, String certificationCode) {
        UserEntity userEntity = userJpaRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Users",id));
        User user = userEntity.to();

        if (!certificationCode.equals(user.certificationCode())) {
            throw new CertificationCodeNotMatchedException();
        }

        user = User.updateCertificationCode(id,certificationCode);
        userRepository.save(user);
    }

    private void sendCertificationEmail(String email, String certificationUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Please certify your email address");
        message.setText("Please click the following link to certify your email address: " + certificationUrl);
        mailSender.send(message);
    }

    private String generateCertificationUrl(User user) {
        return "http://localhost:8080/api/users/" + user.id() + "/verify?certificationCode=" + user.certificationCode();
    }
}