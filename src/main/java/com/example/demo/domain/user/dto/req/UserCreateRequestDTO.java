package com.example.demo.domain.user.dto.req;

import lombok.Builder;

@Builder
public record UserCreateRequestDTO(
        String email,
        String nickName,
        String address
) {
    @Override
    public String toString() {
        return "{" +
                "\"email\":" + (email == null ? "null" : "\"" + email + "\"") + ", " +
                "\"nickName\":" + (nickName == null ? "null" : "\"" + nickName + "\"") + ", " +
                "\"address\":" + (address == null ? "null" : "\"" + address + "\"") +
                "}";
    }
}
