package com.example.demo.user.service;

import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UUIDHolder;
import com.example.demo.support.fake.FakeMailSender;
import com.example.demo.support.fake.FakeUserRepository;
import com.example.demo.support.holder.TestClockHolder;
import com.example.demo.support.holder.TestUUIDHolder;
import com.example.demo.user.constant.UserStatus;
import com.example.demo.user.controller.dto.request.UserCreateDto;
import com.example.demo.user.controller.dto.request.UserUpdateDto;
import com.example.demo.user.controller.port.UserService;
import com.example.demo.user.exception.CertificationCodeNotMatchedException;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.repository.model.User;
import com.example.demo.user.service.port.MailSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

class UserServiceImplTest {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UUIDHolder uuidHolder;
    private final ClockHolder clockHolder;
    private final MailSender mailSender;


    //for test
    private User activeUser;
    private User pendingUser;

    public UserServiceImplTest() {
        this.userRepository = new FakeUserRepository();
        this.clockHolder = new TestClockHolder(123L);
        this.uuidHolder = new TestUUIDHolder("aaaa");
        this.mailSender = new FakeMailSender();
        this.userService = new UserServiceImpl(userRepository, uuidHolder, clockHolder, mailSender);
    }

    @BeforeEach
    void init() {
        pendingUser = User.builder()
                .id(1L)
                .email("lostcatbox@gmail.com")
                .address("Seoul")
                .certificationCode("bbbb")
                .nickname("lostcatbox")
                .lastLoginAt(222L)
                .status(UserStatus.PENDING).build();
        activeUser = User.builder()
                .id(2L)
                .email("lostcatbox@gmail.com")
                .address("Seoul")
                .certificationCode("bbbb")
                .nickname("lostcatbox")
                .lastLoginAt(222L)
                .status(UserStatus.ACTIVE).build();

        userRepository.save(pendingUser);
        userRepository.save(activeUser);

    }

    @Test
    void getById() {
        User findUser = userService.getById(activeUser.getId());

        Assertions.assertAll(
                () -> findUser.getId().equals(activeUser.getId()),
                () -> findUser.getNickname().equals(activeUser.getNickname()),
                () -> findUser.getStatus().equals(activeUser.getStatus()),
                () -> findUser.getAddress().equals(activeUser.getAddress()),
                () -> findUser.getCertificationCode().equals(activeUser.getCertificationCode()),
                () -> findUser.getEmail().equals(activeUser.getEmail()),
                () -> findUser.getLastLoginAt().equals(activeUser.getLastLoginAt())
        );

    }

    @Test
    void getByEmail() {
        User findUser = userService.getByEmail(activeUser.getEmail());

        Assertions.assertAll(
                () -> findUser.getId().equals(activeUser.getId()),
                () -> findUser.getNickname().equals(activeUser.getNickname()),
                () -> findUser.getStatus().equals(activeUser.getStatus()),
                () -> findUser.getAddress().equals(activeUser.getAddress()),
                () -> findUser.getCertificationCode().equals(activeUser.getCertificationCode()),
                () -> findUser.getEmail().equals(activeUser.getEmail()),
                () -> findUser.getLastLoginAt().equals(activeUser.getLastLoginAt())
        );
    }

    @Test
    void updateUser() {
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .nickname("lostcatbox2")
                .address("Busan")
                .build();
        User findUser = userService.updateUser(activeUser.getId(), userUpdateDto);

        Assertions.assertAll(
                () -> findUser.getId().equals(activeUser.getId()),
                () -> findUser.getNickname().equals("lostcatbox2"),
                () -> findUser.getStatus().equals(activeUser.getStatus()),
                () -> findUser.getAddress().equals("Busan"),
                () -> findUser.getCertificationCode().equals(activeUser.getCertificationCode()),
                () -> findUser.getEmail().equals(activeUser.getEmail()),
                () -> findUser.getLastLoginAt().equals(activeUser.getLastLoginAt())
        );
    }

    @Test
    void getByIdOrElseThrow_ACTIVE_USER_성공테스트() {
        User findUser = userService.getByIdOrElseThrow(activeUser.getId());
        Assertions.assertAll(
                () -> findUser.getId().equals(activeUser.getId()),
                () -> findUser.getNickname().equals(activeUser.getNickname()),
                () -> findUser.getStatus().equals(activeUser.getStatus()),
                () -> findUser.getAddress().equals(activeUser.getAddress()),
                () -> findUser.getCertificationCode().equals(activeUser.getCertificationCode()),
                () -> findUser.getEmail().equals(activeUser.getEmail()),
                () -> findUser.getLastLoginAt().equals(activeUser.getLastLoginAt())
        );
    }

    @Test
    void getByIdOrElseThrow_PENDING_USER_실패테스트() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.getByIdOrElseThrow(pendingUser.getId()));
    }

    @Test
    void login_ACTIVE_USER_성공테스트() {
        userService.login(activeUser.getId());
        User loginedUser = userRepository.findById(activeUser.getId());
        Assertions.assertEquals(123L, loginedUser.getLastLoginAt());
    }

    @Test
    void verifyEmail_인증_성공_테스트() {
        userService.verifyEmail(pendingUser.getId(), pendingUser.getCertificationCode());
        User verifiedUser = userRepository.findById(pendingUser.getId());
        Assertions.assertEquals(UserStatus.ACTIVE, verifiedUser.getStatus());
    }

    @Test
    void verifyEmail_인증_실패_테스트() {
        Assertions.assertEquals(UserStatus.PENDING, pendingUser.getStatus());

        Assertions.assertThrows(CertificationCodeNotMatchedException.class, () -> userService.verifyEmail(pendingUser.getId(), "notCertificationCode"));
    }

    @Test
    void createUser() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .email("lostcatbox@gmail.com")
                .nickname("lostcatbox")
                .address("Seoul")
                .build();

        User createdUser = userService.createUser(userCreateDto);

        Assertions.assertAll(
                () -> Objects.nonNull(createdUser.getId()),
                () -> createdUser.getNickname().equals("lostcatbox"),
                () -> createdUser.getStatus().equals(UserStatus.PENDING),
                () -> createdUser.getAddress().equals("Seoul"),
                () -> createdUser.getCertificationCode().equals("bbbb"),
                () -> createdUser.getEmail().equals("lostcatbox@gmail.com"),
                () -> Objects.isNull(createdUser.getLastLoginAt())
        );
    }
}