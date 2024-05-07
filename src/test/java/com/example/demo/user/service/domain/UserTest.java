package com.example.demo.user.service.domain;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UserTest {

    @Test
    public void User_는_UserCreate_로_새로운_객체생성가능하(){
        UserCreate userCreate = UserCreate.builder()
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .build();
        User user = User.from(userCreate);

        Assertions.assertThat(user.getEmail()).isEqualTo(userCreate.getEmail());
        Assertions.assertThat(user.getAddress()).isEqualTo(userCreate.getAddress());
        Assertions.assertThat(user.getNickname()).isEqualTo(userCreate.getNickname());
        Assertions.assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
//        Assertions.assertThat(user.getLastLoginAt()).isEqualTo(UserStatus.PENDING);

    }
}
