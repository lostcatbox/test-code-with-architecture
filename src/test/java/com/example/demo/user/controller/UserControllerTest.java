package com.example.demo.user.controller;

import com.example.demo.support.container.TestUserContainer;
import com.example.demo.user.constant.UserStatus;
import com.example.demo.user.controller.dto.response.UserResponse;
import com.example.demo.user.repository.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class UserControllerTest {
    private final TestUserContainer testUserContainer = new TestUserContainer();


    //for test
    private User activeUser;
    private User pendingUser;

    @BeforeEach
    void init() {
        pendingUser = User.builder()
                .email("lostcatbox@gmail.com")
                .address("Seoul")
                .certificationCode("bbbb")
                .nickname("lostcatbox")
                .lastLoginAt(222L)
                .status(UserStatus.PENDING).build();
        activeUser = User.builder()
                .email("lostcatbox@gmail.com")
                .address("Seoul")
                .certificationCode("cccc")
                .nickname("lostcatbox")
                .lastLoginAt(222L)
                .status(UserStatus.ACTIVE).build();

        testUserContainer.getUserRepository().save(pendingUser);
        testUserContainer.getUserRepository().save(activeUser);

    }

    @Test
    void getUserById() {
        ResponseEntity<UserResponse> userById = testUserContainer.getUserController().getUserById(activeUser.getId());
        UserResponse body = userById.getBody();
        Assertions.assertAll(
                () -> body.getEmail().equals(activeUser.getEmail()),
                () -> body.getId().equals(activeUser.getId()),
                () -> body.getNickname().equals(activeUser.getNickname()),
                () -> body.getStatus().equals(activeUser.getStatus()),
                () -> body.getLastLoginAt().equals(activeUser.getLastLoginAt())
        );
    }

    @Test
    void verifyEmail() {
        ResponseEntity<Void> response = testUserContainer.getUserController().verifyEmail(activeUser.getId(), "cccc");
        response.getStatusCode().is2xxSuccessful();

    }

    @Test
    void getMyInfo() {
    }

    @Test
    void updateMyInfo() {
    }

    @Test
    void toMyProfileResponse() {
    }
}