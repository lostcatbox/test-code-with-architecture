package com.example.demo.support.fake;

import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.user.constant.UserStatus;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.repository.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class FakeUserRepository implements UserRepository {
    private final List<User> dataList = new ArrayList<>();
    private final AtomicLong idx = new AtomicLong(1L);


    @Override
    public User findByIdAndStatus(long id, UserStatus userStatus) {
        return dataList.stream().filter(user -> Long.valueOf(id).equals(user.getId()) && userStatus.equals(user.getStatus())).findAny().orElseThrow(() -> new ResourceNotFoundException("", id));
    }

    @Override
    public User findByEmailAndStatus(String email, UserStatus userStatus) {
        return dataList.stream().filter(user -> email.equals(user.getEmail()) && userStatus.equals(user.getStatus())).findAny().orElseThrow(() -> new ResourceNotFoundException("", email));
    }

    @Override
    public User findById(long id) {
        return dataList.stream().filter(user -> Long.valueOf(id).equals(user.getId())).findAny().orElseThrow(() -> new ResourceNotFoundException("", id));
    }

    @Override
    public User save(User user) {
        if (Objects.isNull(user.getId()) || user.getId().equals(0L)) {
            user.setId(Long.valueOf(String.valueOf(idx.getAndAdd(1L))));
        }
        dataList.add(user);
        return user;
    }
}
