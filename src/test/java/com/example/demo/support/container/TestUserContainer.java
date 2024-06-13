package com.example.demo.support.container;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UUIDHolder;
import com.example.demo.support.fake.FakeMailSender;
import com.example.demo.support.fake.FakeUserRepository;
import com.example.demo.support.holder.TestClockHolder;
import com.example.demo.support.holder.TestUUIDHolder;
import com.example.demo.user.controller.UserController;
import com.example.demo.user.controller.port.UserService;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserServiceImpl;
import com.example.demo.user.service.port.MailSender;
import lombok.Getter;

@Getter
public class TestUserContainer {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UUIDHolder uuidHolder;
    private final ClockHolder clockHolder;
    private final MailSender mailSender;
    private final UserController userController;

    public TestUserContainer() {
        this.userRepository = new FakeUserRepository();
        this.uuidHolder = new TestUUIDHolder("bbbb");
        this.clockHolder = new TestClockHolder(123L);
        this.mailSender = new FakeMailSender();
        this.userService = new UserServiceImpl(userRepository, uuidHolder, clockHolder, mailSender);
        this.userController = new UserController(userService);
    }
}
