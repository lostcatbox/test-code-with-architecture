package com.example.demo.domain.post.dto.res;

import com.example.demo.domain.user.dto.res.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {

    private Long id;
    private String content;
    private Long createdAt;
    private Long modifiedAt;
    private UserResponseDTO writer;
}
