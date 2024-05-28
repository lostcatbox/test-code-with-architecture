package com.example.demo.domain.user.entity.model;

import com.example.demo.common.util.TimeUtil;
import com.example.demo.common.util.UUidCreate;
import com.example.demo.domain.user.dto.req.UserCreateRequestDTO;
import com.example.demo.domain.user.dto.req.UserUpdateRequestDTO;
import com.example.demo.domain.user.enums.UserStatus;
import lombok.Builder;

@Builder
public record User(
        Long id,
        String email,
        String nickName,
        String address,
        String certificationCode,
        UserStatus status,
        Long lastLoginAt
) {
    public static User createUser(UserCreateRequestDTO userCreateRequestDto, UUidCreate uUidCreate) {
        return User.builder()
            .email(userCreateRequestDto.email())
            .nickName(userCreateRequestDto.nickName())
            .address(userCreateRequestDto.address())
            .status(UserStatus.PENDING)
            .certificationCode(uUidCreate.random())
            .build();
    }

    public static User updateUser(long id, UserUpdateRequestDTO userUpdateRequestDto) {
        return User.builder()
                .id(id)
                .nickName(userUpdateRequestDto.nickName())
                .address(userUpdateRequestDto.address())
                .build();
    }

    public static User updateLastLoginAt(long id, TimeUtil timeUtil) {
        return User.builder()
                .id(id)
                .lastLoginAt(timeUtil.thisTime())
                .build();
    }

    public static User updateCertificationCode(long id, String certificationCode) {
        return User.builder()
                .id(id)
                .certificationCode(certificationCode)
                .build();
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id + ", " +
                "\"email\":" + (email == null ? "null" : "\"" + email + "\"") + ", " +
                "\"nickName\":" + (nickName == null ? "null" : "\"" + nickName + "\"") + ", " +
                "\"address\":" + (address == null ? "null" : "\"" + address + "\"") + ", " +
                "\"certificationCode\":" + (certificationCode == null ? "null" : "\"" + certificationCode + "\"") + ", " +
                "\"status\":" + (status == null ? "null" : status) + ", " +
                "\"lastLoginAt\":" + lastLoginAt +
                "}";
    }
}
