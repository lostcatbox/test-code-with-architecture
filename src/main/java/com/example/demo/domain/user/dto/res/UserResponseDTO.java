package com.example.demo.domain.user.dto.res;

import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.enums.UserStatus;
import lombok.Builder;

@Builder
public record UserResponseDTO(
        Long id,
        String email,
        String nickName,
        UserStatus status,
        Long lastLoginAt
) {
    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id + ", " +
                "\"email\":" + (email == null ? "null" : "\"" + email + "\"") + ", " +
                "\"nickName\":" + (nickName == null ? "null" : "\"" + nickName + "\"") + ", " +
                "\"status\":" + (status == null ? "null" : "\"" + status + "\"") + ", " +
                "\"lastLoginAt\":" + lastLoginAt +
                "}";
    }

    public static UserResponseDTO of(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickName(user.getNickname())
                .status(user.getStatus())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
