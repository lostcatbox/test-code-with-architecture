package com.example.demo.post.service.port;

import com.example.demo.post.infrastructure.PostEntity;
import com.example.demo.post.infrastructure.PostJpaRepository;

import java.util.Optional;

public interface PostRepository {
    Optional<PostEntity> findById(long id);

    PostEntity save(PostEntity postEntity);
}
