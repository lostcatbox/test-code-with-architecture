package com.example.demo.user.controller.dto.response;

import com.example.demo.user.constant.UserStatus;
import com.example.demo.user.repository.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {

    private Long id;
    private String email;
    private String nickname;
    private UserStatus status;
    private Long lastLoginAt;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .status(user.getStatus())
                .lastLoginAt(user.getLastLoginAt()).build();
        }
    }

