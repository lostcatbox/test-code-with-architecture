package com.example.demo.user.controller;

import com.example.demo.user.controller.dto.request.UserCreateDto;
import com.example.demo.user.controller.dto.response.UserResponse;
import com.example.demo.user.repository.model.User;
import com.example.demo.user.service.UserServiceImpl;
import com.example.demo.user.service.port.MailSender;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저(users)")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCreateController {

    private final UserServiceImpl userServiceImpl;
    private final MailSender mailSender;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateDto userCreateDto) {
        User user = userServiceImpl.createUser(userCreateDto);
        mailSender.sendCertificationEmail(user.getEmail(), user.getId(), user.getCertificationCode());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponse.from(user));
    }

}