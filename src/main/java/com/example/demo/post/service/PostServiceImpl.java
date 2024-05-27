package com.example.demo.post.service;

import com.example.demo.post.controller.dto.request.PostCreateDto;
import com.example.demo.post.controller.dto.request.PostUpdateDto;
import com.example.demo.post.controller.port.PostService;
import com.example.demo.post.repository.model.Post;
import com.example.demo.post.service.port.PostRepository;

import com.example.demo.user.service.UserServiceImpl;
import com.example.demo.user.repository.model.User;
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
        postRepository.save(post);
        return postRepository.findById(post.getId());
    }

    @Override
    public Post updatePost(long id, PostUpdateDto postUpdateDto) {
        Post post = getPostById(id);
        post.update(postUpdateDto);
        postRepository.save(post);
        return postRepository.findById(post.getId());
    }
}