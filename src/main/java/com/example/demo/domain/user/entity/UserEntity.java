package com.example.demo.domain.user.entity;

import com.example.demo.domain.user.entity.model.User;
import com.example.demo.domain.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@Table(name = "users")
@AllArgsConstructor
@Getter
public class UserEntity {

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

    public static UserEntity from(User user) {
        return UserEntity.builder()
                .id(user.id())
                .email(user.email())
                .nickname(user.nickName())
                .address(user.address())
                .certificationCode(user.certificationCode())
                .status(user.status())
                .lastLoginAt(user.lastLoginAt())
                .build();
    }

    public User to() {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .nickName(this.nickname)
                .address(this.address)
                .certificationCode(this.certificationCode)
                .status(this.status)
                .lastLoginAt(this.lastLoginAt)
                .build();
    }


}