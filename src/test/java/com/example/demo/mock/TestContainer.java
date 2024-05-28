package com.example.demo.mock;

import com.example.demo.common.util.TimeUtil;
import com.example.demo.common.util.UUidCreate;
import com.example.demo.domain.user.controller.UserController;
import com.example.demo.domain.user.controller.UserCreateController;
import com.example.demo.domain.user.repository.impl.UserRepository;
import com.example.demo.domain.user.service.impl.UserServiceImpl;
import lombok.Builder;

public class TestContainer {
    public final UserRepository userRepository;
    public final UserController userController;
    public final UserCreateController userCreateController;

    @Builder
    public TestContainer(UUidCreate uUidCreate, TimeUtil timeUtil) {
        this.userRepository = new FakeUserRepository();
        UserServiceImpl userService = UserServiceImpl.builder()
                .uUidCreate(uUidCreate)
                .timeUtil(timeUtil)
                .userRepository(this.userRepository)
                .build();
        this.userController = UserController.builder()
                .userService(userService)
                .build();
        this.userCreateController = UserCreateController.builder()
                .userService(userService)
                .build();
    }
}
