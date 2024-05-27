package com.example.demo.domain.user.dto.res;

import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.enums.UserStatus;
import lombok.Builder;

@Builder
public record MyProfileResponseDTO(
        Long id,
        String email,
        String address,
        String nickName,
        UserStatus status,
        Long lastLoginAt
) {
    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id + ", " +
                "\"email\":" + (email == null ? "null" : "\"" + email + "\"") + ", " +
                "\"address\":" + (address == null ? "null" : "\"" + address + "\"") + ", " +
                "\"status\":" + (status == null ? "null" : status) + ", " +
                "\"lastLoginAt\":" + lastLoginAt +
                "}";
    }

    public static MyProfileResponseDTO of(User user) {
        return MyProfileResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickName(user.getNickname())
                .status(user.getStatus())
                .address(user.getAddress())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
