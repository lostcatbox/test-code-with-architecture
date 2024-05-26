package com.example.demo.post.controller.port;

import com.example.demo.post.controller.dto.request.PostCreateDto;
import com.example.demo.post.controller.dto.request.PostUpdateDto;
import com.example.demo.post.repository.entity.PostEntity;

public interface PostService {
    PostEntity getPostById(long id);

    PostEntity createPost(PostCreateDto postCreateDto);

    PostEntity updatePost(long id, PostUpdateDto postUpdateDto);
}
