package com.example.demo.user.controller;

import com.example.demo.user.controller.dto.response.MyProfileResponse;
import com.example.demo.user.controller.dto.response.UserResponse;
import com.example.demo.user.controller.dto.request.UserUpdateDto;
import com.example.demo.user.service.UserServiceImpl;
import com.example.demo.user.service.model.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저(users)")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @ResponseStatus
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        return ResponseEntity
            .ok()
            .body(toResponse(userServiceImpl.getByIdOrElseThrow(id)));
    }

    @GetMapping("/{id}/verify")
    public ResponseEntity<Void> verifyEmail(
        @PathVariable long id,
        @RequestParam String certificationCode) {
        userServiceImpl.verifyEmail(id, certificationCode);
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create("http://localhost:3000"))
            .build();
    }

    @GetMapping("/me")
    public ResponseEntity<MyProfileResponse> getMyInfo(
        @Parameter(name = "EMAIL", in = ParameterIn.HEADER)
        @RequestHeader("EMAIL") String email // 일반적으로 스프링 시큐리티를 사용한다면 UserPrincipal 에서 가져옵니다.
    ) {
        User user = userServiceImpl.getByEmail(email);
        return ResponseEntity
            .ok()
            .body(toMyProfileResponse(user));
    }

    @PutMapping("/me")
    @Parameter(in = ParameterIn.HEADER, name = "EMAIL")
    public ResponseEntity<MyProfileResponse> updateMyInfo(
        @Parameter(name = "EMAIL", in = ParameterIn.HEADER)
        @RequestHeader("EMAIL") String email, // 일반적으로 스프링 시큐리티를 사용한다면 UserPrincipal 에서 가져옵니다.
        @RequestBody UserUpdateDto userUpdateDto
    ) {
        User user = userServiceImpl.getByEmail(email);
        userServiceImpl.updateUser(user.getId(), userUpdateDto);
        User response = userServiceImpl.getByEmail(email);
        return ResponseEntity
            .ok()
            .body(toMyProfileResponse(response));
    }

    public UserResponse toResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setNickname(user.getNickname());
        userResponse.setStatus(user.getStatus());
        userResponse.setLastLoginAt(user.getLastLoginAt());
        return userResponse;
    }

    public MyProfileResponse toMyProfileResponse(User user) {
        MyProfileResponse myProfileResponse = new MyProfileResponse();
        myProfileResponse.setId(user.getId());
        myProfileResponse.setEmail(user.getEmail());
        myProfileResponse.setNickname(user.getNickname());
        myProfileResponse.setStatus(user.getStatus());
        myProfileResponse.setAddress(user.getAddress());
        myProfileResponse.setLastLoginAt(user.getLastLoginAt());
        return myProfileResponse;
    }
}