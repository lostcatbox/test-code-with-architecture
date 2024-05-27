package com.example.demo.domain.user.entity;

import com.example.demo.domain.user.dto.req.UserCreateRequestDTO;
import com.example.demo.domain.user.dto.req.UserUpdateRequestDTO;
import com.example.demo.domain.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Entity
@NoArgsConstructor
@Table(name = "users")
@AllArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "address")
    private String address;

    @Column(name = "certification_code")
    private String certificationCode;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "last_login_at")
    private Long lastLoginAt;

    public static User create(UserCreateRequestDTO dto) {
        return User.builder()
                .email(dto.email())
                .nickname(dto.nickName())
                .address(dto.address())
                .status(UserStatus.PENDING)
                .certificationCode(UUID.randomUUID().toString())
                .build();
    }

    public void update(UserUpdateRequestDTO dto) {
        this.nickname = dto.nickName();
        this.address = dto.address();
    }

    public void updateLastLoginAt(Long longAt) {
        this.lastLoginAt = longAt;
    }

    public void updateStatus(UserStatus status) {
        this.status = status;
    }
}