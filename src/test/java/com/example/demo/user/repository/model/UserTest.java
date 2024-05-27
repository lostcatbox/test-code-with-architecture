package com.example.demo.user.repository.model;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UUIDHolder;
import com.example.demo.support.fake.FakeMailSender;
import com.example.demo.support.holder.TestClockHolder;
import com.example.demo.support.holder.TestUUIDHolder;
import com.example.demo.user.constant.UserStatus;
import com.example.demo.user.controller.dto.request.UserCreateDto;
import com.example.demo.user.controller.dto.request.UserUpdateDto;
import com.example.demo.user.exception.CertificationCodeNotMatchedException;
import com.example.demo.user.service.port.MailSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
                () -> assertEquals("lostcatbox@gmail.com", createdUser.getEmail()),
                () -> assertEquals("lostcatbox", createdUser.getNickname()),
                () -> assertEquals("Seoul", createdUser.getAddress()),
                () -> assertEquals("aaaa", createdUser.getCertificationCode()),
                () -> assertEquals(UserStatus.PENDING, createdUser.getStatus()));
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
                () -> assertEquals("lostcatbox@gmail.com", updateUser.getEmail()),
                () -> assertEquals("lostcatbox2", updateUser.getNickname()),
                () -> assertEquals("Busan", updateUser.getAddress()),
                () -> assertEquals("aaaa", updateUser.getCertificationCode()),
                () -> assertEquals(UserStatus.PENDING, updateUser.getStatus())
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
                () -> assertEquals(1L, login.getId()),
                () -> assertEquals("lostcatbox@gmail.com", login.getEmail()),
                () -> assertEquals("lostcatbox", login.getNickname()),
                () -> assertEquals("Seoul", login.getAddress()),
                () -> assertEquals("bbbb", login.getCertificationCode()),
                () -> assertEquals(UserStatus.PENDING, login.getStatus()),
                () -> assertEquals(111L, login.getLastLoginAt())
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

        Assertions.assertDoesNotThrow(() -> originUser.verifyCertificationCode("bbbb"));
        Assertions.assertEquals(UserStatus.ACTIVE, originUser.getStatus());

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

        Assertions.assertThrows(CertificationCodeNotMatchedException.class, () -> originUser.verifyCertificationCode("abcd"));
    }
}