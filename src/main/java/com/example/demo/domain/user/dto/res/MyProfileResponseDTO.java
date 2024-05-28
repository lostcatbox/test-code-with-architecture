package com.example.demo.domain.user.dto.res;

import com.example.demo.domain.user.entity.model.User;
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
                .id(user.id())
                .email(user.email())
                .nickName(user.nickName())
                .status(user.status())
                .address(user.address())
                .lastLoginAt(user.lastLoginAt())
                .build();
    }
}
