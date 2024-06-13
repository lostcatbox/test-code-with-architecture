package com.example.demo.post.service.port;

import com.example.demo.post.repository.model.Post;

public interface PostRepository {

    Post findById(long id);

    Post save(Post post);
}