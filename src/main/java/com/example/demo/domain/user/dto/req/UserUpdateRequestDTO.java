package com.example.demo.domain.user.dto.req;

import lombok.Builder;

@Builder
public record UserUpdateRequestDTO(
        String nickName,
        String address
) {
    @Override
    public String toString() {
        return "{" +
                "\"nickName\":" + (nickName == null ? "null" : "\"" + nickName + "\"") + ", " +
                "\"address\":" + (address == null ? "null" : "\"" + address + "\"") +
                "}";
    }
}
