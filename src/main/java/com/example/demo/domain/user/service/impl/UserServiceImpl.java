package com.example.demo.domain.user.service.impl;

import com.example.demo.common.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.domain.user.dto.req.UserCreateRequestDTO;
import com.example.demo.domain.user.dto.req.UserUpdateRequestDTO;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.enums.UserStatus;
import com.example.demo.domain.user.repository.impl.UserJpaRepository;
import com.example.demo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final JavaMailSender mailSender;

    public User getByEmail(String email) {
        return userJpaRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Users", email));
    }

    public User getById(long id) {
        return userJpaRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Transactional
    public User create(UserCreateRequestDTO userCreateRequestDto) {
        User user = User.create(userCreateRequestDto);
        user = userJpaRepository.save(user);
        String certificationUrl = generateCertificationUrl(user);
        sendCertificationEmail(userCreateRequestDto.email(), certificationUrl);
        return user;
    }

    @Transactional
    public User update(long id, UserUpdateRequestDTO userUpdateRequestDto) {
        User user = getById(id);
        user.update(userUpdateRequestDto);
        user = userJpaRepository.save(user);
        return user;
    }

    @Transactional
    public void login(long id) {
        User user = userJpaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
        user.updateLastLoginAt(Clock.systemUTC().millis());
    }

    @Transactional
    public void verifyEmail(long id, String certificationCode) {
        User user = userJpaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
        if (!certificationCode.equals(user.getCertificationCode())) {
            throw new CertificationCodeNotMatchedException();
        }
        user.updateStatus(UserStatus.ACTIVE);
    }

    private void sendCertificationEmail(String email, String certificationUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Please certify your email address");
        message.setText("Please click the following link to certify your email address: " + certificationUrl);
        mailSender.send(message);
    }

    private String generateCertificationUrl(User user) {
        return "http://localhost:8080/api/users/" + user.getId() + "/verify?certificationCode=" + user.getCertificationCode();
    }
}