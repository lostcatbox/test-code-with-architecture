package com.example.demo.user.container;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UuidHolder;
import com.example.demo.mock.FackUserRepository;
import com.example.demo.mock.FakeMailSender;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import com.example.demo.user.controller.UserController;
import com.example.demo.user.controller.port.UserService;
import com.example.demo.user.service.CertificationService;
import com.example.demo.user.service.UserServiceImpl;
import com.example.demo.user.service.port.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestContainer {
    private UserController controller;
    private UserService service;
    private UserRepository repository;
    private CertificationService certificationService;

    private ClockHolder clockHolder;
    private UuidHolder uuidHolder;

    public TestContainer getInstance(){
        this.repository = new FackUserRepository();
        this.certificationService = new CertificationService(new FakeMailSender());
        this.clockHolder = new TestClockHolder(1L);
        this.uuidHolder = new TestUuidHolder("aaaa");
        this.service = new UserServiceImpl(repository, certificationService, clockHolder, uuidHolder);
        this.controller = new UserController(service);






        return this;
    }

}
