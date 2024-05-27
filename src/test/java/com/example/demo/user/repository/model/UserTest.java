package com.example.demo.user.repository.model;

import com.example.demo.support.fake.FakeMailSender;
import com.example.demo.support.holder.TestClockHolder;
import com.example.demo.support.holder.TestUUIDHolder;
import com.example.demo.user.constant.UserStatus;
import com.example.demo.user.controller.dto.request.UserCreateDto;
import com.example.demo.user.controller.dto.request.UserUpdateDto;
import com.example.demo.user.exception.CertificationCodeNotMatchedException;
import com.example.demo.user.service.port.MailSender;
import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UUIDHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private final MailSender mailSender;
    private final UUIDHolder uuidHolder;
    private final ClockHolder clockHolder;

    public UserTest() {
        this.mailSender = new FakeMailSender();
        this.uuidHolder = new TestUUIDHolder("aaaa");
        this.clockHolder = new TestClockHolder(111L);
    }

    @Test
    void createUser_성공_테스트() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .email("lostcatbox@gmail.com")
                .nickname("lostcatbox")
                .address("Seoul")
                .build();
        User createdUser = User.from(userCreateDto, uuidHolder);
        Assertions.assertAll(
                () -> assertNull(createdUser.getId()),
                () -> assertEquals(createdUser.getEmail(), "lostcatbox@gmail.com"),
                () -> assertEquals(createdUser.getNickname(), "lostcatbox"),
                () -> assertEquals(createdUser.getAddress(), "Seoul"),
                () -> assertEquals(createdUser.getCertificationCode(), "aaaa"),
                ()-> assertEquals(createdUser.getStatus(), UserStatus.PENDING)
        );
    }

    @Test
    void updateUser() {
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .nickname("lostcatbox2")
                .address("Busan")
                .build();

        User originUser = User.builder()
                .id(1L)
                .email("lostcatbox@gmail.com")
                .address("Seoul")
                .certificationCode("bbbb")
                .nickname("lostcatbox")
                .lastLoginAt(123L)
                .status(UserStatus.PENDING).build();

        User updateUser = originUser.updateUser(userUpdateDto, uuidHolder);
        Assertions.assertAll(
                () -> assertEquals(updateUser.getId(), 1L),
                () -> assertEquals(updateUser.getEmail(), "lostcatbox@gmail.com"),
                () -> assertEquals(updateUser.getNickname(), "lostcatbox2"),
                () -> assertEquals(updateUser.getAddress(), "Busan"),
                () -> assertEquals(updateUser.getCertificationCode(), "aaaa"),
                ()-> assertEquals(updateUser.getStatus(), UserStatus.PENDING)
        );
    }

    @Test
    void login() {
        User originUser = User.builder()
                .id(1L)
                .email("lostcatbox@gmail.com")
                .address("Seoul")
                .certificationCode("bbbb")
                .nickname("lostcatbox")
                .lastLoginAt(123L)
                .status(UserStatus.PENDING).build();

        User login = originUser.login(clockHolder);
        Assertions.assertAll(
                () -> assertEquals(login.getId(), 1L),
                () -> assertEquals(login.getEmail(), "lostcatbox@gmail.com"),
                () -> assertEquals(login.getNickname(), "lostcatbox"),
                () -> assertEquals(login.getAddress(), "Seoul"),
                () -> assertEquals(login.getCertificationCode(), "bbbb"),
                ()-> assertEquals(login.getStatus(), UserStatus.PENDING),
                () -> assertEquals(login.getLastLoginAt(), 111L)
        );
    }

    @Test
    void checkCertificationCode_성공테스트() {
        User originUser = User.builder()
                .id(1L)
                .email("lostcatbox@gmail.com")
                .address("Seoul")
                .certificationCode("bbbb")
                .nickname("lostcatbox")
                .lastLoginAt(123L)
                .status(UserStatus.PENDING).build();

        Assertions.assertDoesNotThrow(()->originUser.checkCertificationCode("bbbb"));;
    }
    @Test
    void checkCertificationCode_실패테스트() {
        User originUser = User.builder()
                .id(1L)
                .email("lostcatbox@gmail.com")
                .address("Seoul")
                .certificationCode("bbbb")
                .nickname("lostcatbox")
                .lastLoginAt(123L)
                .status(UserStatus.PENDING).build();

        Assertions.assertThrows(CertificationCodeNotMatchedException.class,()->originUser.checkCertificationCode("abcd"));
    }
}