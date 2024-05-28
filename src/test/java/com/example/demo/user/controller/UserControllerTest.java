package com.example.demo.user.controller;

import com.example.demo.domain.user.dto.res.UserResponseDTO;
import com.example.demo.domain.user.entity.model.User;
import com.example.demo.domain.user.enums.UserStatus;
import com.example.demo.mock.TestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserControllerTest {
    @Test
    void createUser() {
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("aehdals9900@gmail.com")
                .nickName("zl존파워동민")
                .address("서울 봉천동")
                .status(UserStatus.ACTIVE)
                .certificationCode("eiwojfoipqjopvif-woaifjvaseiovfsoe-fwesaovijaseovfj-erasfojv")
                .lastLoginAt(2000L)
                .build());

        // try
        ResponseEntity<UserResponseDTO> res = testContainer.userController.getUserById(1);

        // responseCheck
        assertThat(res.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(res.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(res.getBody()).email()).isEqualTo("aehdals9900@gmail.com");
        assertThat(res.getBody().nickName()).isEqualTo("zl존파워동민");
        assertThat(res.getBody().lastLoginAt()).isEqualTo(2000L);
        assertThat(res.getBody().status()).isEqualTo(UserStatus.ACTIVE);
    }
}
