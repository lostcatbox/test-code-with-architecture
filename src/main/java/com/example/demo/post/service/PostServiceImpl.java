package com.example.demo.post.service;

import com.example.demo.post.controller.dto.request.PostCreateDto;
import com.example.demo.post.controller.dto.request.PostUpdateDto;
import com.example.demo.post.controller.port.PostService;
import com.example.demo.post.repository.entity.PostEntity;
import com.example.demo.post.service.model.Post;
import com.example.demo.post.service.port.PostRepository;
import com.example.demo.user.repository.entity.UserEntity;
import java.time.Clock;

import com.example.demo.user.service.UserServiceImpl;
import com.example.demo.user.service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserServiceImpl userServiceImpl;

    @Override
    public Post getPostById(long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post createPost(PostCreateDto postCreateDto) {
        User user= userServiceImpl.getById(postCreateDto.getWriterId());
        Post post = Post.create(postCreateDto, user);
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(long id, PostUpdateDto postUpdateDto) {
        Post post = getPostById(id);
        post.update(postUpdateDto);
        return postRepository.save(post);
    }
}