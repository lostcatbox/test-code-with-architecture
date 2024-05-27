package com.example.demo.user.controller.dto.request;

import com.example.demo.user.repository.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.Clock;
import java.util.UUID;

@Getter
public class UserCreateDto {

    private final String email;
    private final String nickname;
    private final String address;

    @Builder
    public UserCreateDto(
        @JsonProperty("email") String email,
        @JsonProperty("nickname") String nickname,
        @JsonProperty("address") String address) {
        this.email = email;
        this.nickname = nickname;
        this.address = address;
    }

    public User toUser() {
        return User.builder()
                .email(this.getEmail())
                .nickname(this.getNickname())
                .address(this.getAddress())
                .certificationCode(UUID.randomUUID().toString())
                .lastLoginAt(Clock.systemUTC().millis())
                .build();
    }
}
