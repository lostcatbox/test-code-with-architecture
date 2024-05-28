package com.example.demo.domain.user.controller;

import com.example.demo.domain.user.dto.req.UserCreateRequestDTO;
import com.example.demo.domain.user.dto.res.UserResponseDTO;
import com.example.demo.domain.user.entity.model.User;
import com.example.demo.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
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
@Builder
public class UserCreateController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateRequestDTO userCreateRequestDto) {
        User userEntity = userService.create(userCreateRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponseDTO.of(userEntity));
    }
}