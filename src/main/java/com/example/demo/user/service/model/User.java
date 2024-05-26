package com.example.demo.user.service.model;

import com.example.demo.core.infrastructure.MailSender;
import com.example.demo.user.controller.dto.request.UserCreateDto;
import com.example.demo.user.controller.dto.request.UserUpdateDto;
import com.example.demo.user.exception.CertificationCodeNotMatchedException;
import com.example.demo.user.model.UserStatus;
import com.example.demo.user.repository.entity.UserEntity;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String nickname;
    private String address;
    private String certificationCode;
    private UserStatus status;
    private Long lastLoginAt;

    @Transactional
    public User createUser(UserCreateDto userCreateDto, MailSender mailSender) {
        User user = User.builder()
                .email(userCreateDto.getEmail())
                .nickname(userCreateDto.getNickname())
                .address(userCreateDto.getAddress())
                .status(UserStatus.PENDING)
                .certificationCode(UUID.randomUUID().toString())
                .build();

        String certificationUrl = mailSender.generateCertificationUrl(user);
        mailSender.sendCertificationEmail(userCreateDto.getEmail(), certificationUrl);
        return user;
    }
    public User updateUser(UserUpdateDto userUpdateDto) {
        User user = User.builder()
                .id(this.getId())
                .email(this.getEmail())
                .nickname(userUpdateDto.getNickname())
                .address(userUpdateDto.getAddress())
                .status(UserStatus.PENDING)
                .certificationCode(UUID.randomUUID().toString())
                .build();
        return user;
    }

    public User setLastLoginAt(long millis) {
        this.lastLoginAt = millis;
        return this;
    }

    public void checkCertificationCode(String certificationCode) {
        if (!certificationCode.equals(this.getCertificationCode())) {
            throw new CertificationCodeNotMatchedException();
        }
    }
}
