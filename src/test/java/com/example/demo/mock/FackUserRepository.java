package com.example.demo.mock;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.service.port.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FackUserRepository implements UserRepository {
    private AtomicLong idx = new AtomicLong(0);
    private final List<User> data = Collections.synchronizedList(new ArrayList<>());
    @Override
    public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
        return data.stream().filter(com ->com.getId().equals(id)&&com.getStatus().equals(userStatus)).findAny();

    }

    @Override
    public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
        return data.stream().filter(com ->com.getEmail().equals(email)&&com.getStatus().equals(userStatus)).findAny();
    }

    @Override
    public Optional<User> findById(long id) {
        return data.stream().filter(com ->com.getId().equals(id)).findAny();
    }

    @Override
    public User save(User user) {
        if(user.getId()==null||user.getId()==0){

            User savedUser = User.builder()
                    .id(idx.incrementAndGet())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .address(user.getAddress())
                    .certificationCode(user.getCertificationCode())
                    .status(user.getStatus())
                    .lastLoginAt(user.getLastLoginAt())
                    .build();
            data.add(savedUser);
            return savedUser;
        }else{
            data.removeIf(item -> item.getId().equals(user.getId()));
            data.add(user);
            return user;
        }

    }
}
