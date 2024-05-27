package com.example.demo.user.repository.model;

import com.example.demo.user.controller.dto.request.UserCreateDto;
import com.example.demo.user.controller.dto.request.UserUpdateDto;
import com.example.demo.user.exception.CertificationCodeNotMatchedException;
import com.example.demo.user.constant.UserStatus;
import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UUIDHolder;
import lombok.*;

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

    public static User from(UserCreateDto userCreateDto, UUIDHolder uuidHolder) {
        return User.builder()
                .nickname(userCreateDto.getNickname())
                .email(userCreateDto.getEmail())
                .address(userCreateDto.getAddress())
                .certificationCode(uuidHolder.randomUUID())
                .status(UserStatus.PENDING).build();
    }

    public User updateUser(UserUpdateDto userUpdateDto, UUIDHolder uuidHolder) {
        User user = User.builder()
                .id(this.getId())
                .email(this.getEmail())
                .nickname(userUpdateDto.getNickname())
                .address(userUpdateDto.getAddress())
                .status(UserStatus.PENDING)
                .certificationCode(uuidHolder.randomUUID())
                .build();
        return user;
    }

    public User login(ClockHolder clockHolder) {
        this.lastLoginAt = clockHolder.millis();
        return this;
    }

    public void verifyCertificationCode(String certificationCode) {
        if (!certificationCode.equals(this.getCertificationCode())) {
            throw new CertificationCodeNotMatchedException();
        }
        this.setStatus(UserStatus.ACTIVE);
    }
}
