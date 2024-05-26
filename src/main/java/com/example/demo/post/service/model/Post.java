package com.example.demo.post.service.model;

import com.example.demo.post.controller.dto.request.PostCreateDto;
import com.example.demo.post.controller.dto.request.PostUpdateDto;
import com.example.demo.user.repository.entity.UserEntity;
import com.example.demo.user.service.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Clock;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Post {
    private Long id;
    private String content;
    private Long createdAt;
    private Long modifiedAt;
    private User writer;


    public static Post create(PostCreateDto postCreateDto, User user) {
        return Post.builder()
                .writer(user)
                .content(postCreateDto.getContent())
                .createdAt(Clock.systemUTC().millis())
                .build();
    }

    public Post update(PostUpdateDto postUpdateDto) {
        return Post.builder()
                .id(this.id)
                .content(postUpdateDto.getContent())
                .createdAt(this.createdAt)
                .modifiedAt(Clock.systemUTC().millis())
                .build();

    }
}
