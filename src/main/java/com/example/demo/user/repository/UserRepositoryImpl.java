package com.example.demo.user.repository;

import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.post.repository.PostJPARepository;
import com.example.demo.post.repository.entity.PostEntity;
import com.example.demo.user.constant.UserStatus;
import com.example.demo.user.repository.entity.UserEntity;
import com.example.demo.user.repository.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJPARepository userJPARepository;
    private final PostJPARepository postJPARepository;
    @Override
    public User findByIdAndStatus(long id, UserStatus userStatus) {
        Optional<PostEntity> byId = postJPARepository.findById(1L);
        byId.get()// send mail같은 코드
        return userJPARepository.findByIdAndStatus(id, userStatus).orElseThrow(() -> new ResourceNotFoundException("에러", id))
                .toModel();
    }

    @Override
    public User findByEmailAndStatus(String email, UserStatus userStatus) {
        return userJPARepository.findByEmailAndStatus(email, userStatus).orElseThrow(()->new ResourceNotFoundException("에러",1))
                .toModel();
    }

    @Override
    public User findById(long id) {
        return userJPARepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("s", id)).toModel();
    }

    @Override
    public void save(User user){
        userJPARepository.save(UserEntity.from(user));
    }
}
