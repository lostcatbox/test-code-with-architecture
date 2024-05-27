package com.example.demo.domain.post.service;

import com.example.demo.domain.user.service.UserService;
import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.domain.post.dto.query.PostCreateDto;
import com.example.demo.domain.post.dto.query.PostUpdateDto;
import com.example.demo.domain.post.repository.PostEntity;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.user.entity.User;
import java.time.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostEntity getPostById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public PostEntity create(PostCreateDto postCreateDto) {
        User user = userService.getById(postCreateDto.getWriterId());
        PostEntity postEntity = new PostEntity();
        postEntity.setWriter(user);
        postEntity.setContent(postCreateDto.getContent());
        postEntity.setCreatedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }

    public PostEntity update(long id, PostUpdateDto postUpdateDto) {
        PostEntity postEntity = getPostById(id);
        postEntity.setContent(postUpdateDto.getContent());
        postEntity.setModifiedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }
}