package com.example.demo.post.infrastructure;

import com.example.demo.post.service.port.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;
    @Override
    public Optional<PostEntity> findById(long id) {
        return postJpaRepository.findById(id);
    }

    @Override
    public PostEntity save(PostEntity postEntity) {
        return postJpaRepository.save(postEntity);
    }
}
