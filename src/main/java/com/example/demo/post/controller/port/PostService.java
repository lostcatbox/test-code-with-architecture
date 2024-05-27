package com.example.demo.post.controller.port;

import com.example.demo.post.controller.dto.request.PostCreateDto;
import com.example.demo.post.controller.dto.request.PostUpdateDto;
import com.example.demo.post.repository.entity.PostEntity;
import com.example.demo.post.repository.model.Post;

public interface PostService {
    Post getPostById(long id);

    Post createPost(PostCreateDto postCreateDto);

    Post updatePost(long id, PostUpdateDto postUpdateDto);
}
