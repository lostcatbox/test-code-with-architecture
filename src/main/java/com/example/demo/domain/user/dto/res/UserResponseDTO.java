package com.example.demo.domain.user.dto.res;

import com.example.demo.domain.user.entity.model.User;
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
                .id(user.id())
                .email(user.email())
                .nickName(user.nickName())
                .status(user.status())
                .lastLoginAt(user.lastLoginAt())
                .build();
    }
}
