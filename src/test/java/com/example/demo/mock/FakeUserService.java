package com.example.demo.mock;

import com.example.demo.common.domain.ResourceNotFoundException;
import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UuidHolder;
import com.example.demo.user.controller.port.UserService;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.service.CertificationService;
import com.example.demo.user.service.port.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Builder
@NoArgsConstructor
public class FakeUserService implements UserService {

    private UserRepository repository;
    private  CertificationService certificationService;
    private  ClockHolder clockHolder;
    private  UuidHolder uuidHolder;


    @Override
    public User getByEmail(String email) {
        Optional<User> byEmailAndStatus = repository.findByEmailAndStatus(email, UserStatus.ACTIVE);
        return byEmailAndStatus.get();
    }

    @Override
    public User getById(long id) {
        User byId = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
        return byId;
    }

    @Override
    public User create(UserCreate userCreate) {
        User from = User.from(userCreate, uuidHolder);
        User save = repository.save(from);
        return save;
    }

    @Override
    public User update(long id, UserUpdate userUpdate) {
        User user = getById(id);
        User updatedUser = user.update(userUpdate, clockHolder);
        return updatedUser;
    }

    @Override
    public void login(long id) {
        Optional<User> byId = repository.findById(id);
        byId.get().login(clockHolder);
    }

    @Override
    public void verifyEmail(long id, String certificationCode) {
        Optional<User> byId = repository.findById(id);
        User user = byId.get().verifyEmail(certificationCode);
        repository.save(user);
    }
}
