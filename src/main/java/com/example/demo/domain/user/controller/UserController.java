package com.example.demo.domain.user.controller;

import com.example.demo.domain.user.service.UserService;
import com.example.demo.domain.user.dto.res.MyProfileResponseDTO;
import com.example.demo.domain.user.dto.res.UserResponseDTO;
import com.example.demo.domain.user.dto.req.UserUpdateRequestDTO;
import com.example.demo.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "유저(users)")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable long id) {
        return ResponseEntity
                .ok()
                .body(UserResponseDTO.of(userService.getById(id)));
    }

    @GetMapping("/{id}/verify")
    public ResponseEntity<Void> verifyEmail(
            @PathVariable long id,
            @RequestParam String certificationCode) {
        userService.verifyEmail(id, certificationCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:3000"))
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<MyProfileResponseDTO> getMyInfo(
            @Parameter(name = "EMAIL", in = ParameterIn.HEADER)
            @RequestHeader("EMAIL") String email // 일반적으로 스프링 시큐리티를 사용한다면 UserPrincipal 에서 가져옵니다.
    ) {
        User user = userService.getByEmail(email);
        userService.login(user.getId());
        return ResponseEntity
                .ok()
                .body(MyProfileResponseDTO.of(user));
    }

    @PutMapping("/me")
    @Parameter(in = ParameterIn.HEADER, name = "EMAIL")
    public ResponseEntity<MyProfileResponseDTO> updateMyInfo(
            @Parameter(name = "EMAIL", in = ParameterIn.HEADER)
            @RequestHeader("EMAIL") String email, // 일반적으로 스프링 시큐리티를 사용한다면 UserPrincipal 에서 가져옵니다.
            @RequestBody UserUpdateRequestDTO userUpdateRequestDto
    ) {
        User user = userService.getByEmail(email);
        user = userService.update(user.getId(), userUpdateRequestDto);
        return ResponseEntity
                .ok()
                .body(MyProfileResponseDTO.of(user));
    }
}