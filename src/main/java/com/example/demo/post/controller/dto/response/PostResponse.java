package com.example.demo.post.controller.dto.response;

import com.example.demo.post.repository.model.Post;
import com.example.demo.user.controller.UserController;
import com.example.demo.user.controller.dto.response.UserResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostResponse {

    private Long id;
    private String content;
    private Long createdAt;
    private Long modifiedAt;
    private UserResponse writer;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .writer(UserResponse.from(post.getWriter()))
                .build();
    }
}
