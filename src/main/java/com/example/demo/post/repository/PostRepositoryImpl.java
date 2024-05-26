package com.example.demo.post.repository;

import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.post.repository.entity.PostEntity;
import com.example.demo.post.service.model.Post;
import com.example.demo.post.service.port.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJPARepository postJPARepository;
    @Override
    public Post findById(long id) {
        return postJPARepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    @Override
    public void save(Post postEntity) {
        postJPARepository.save(postEntity);
    }
}
