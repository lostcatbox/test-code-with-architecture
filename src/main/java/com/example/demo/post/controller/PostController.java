package com.example.demo.post.controller;

import com.example.demo.post.controller.dto.request.PostUpdateDto;
import com.example.demo.post.controller.dto.response.PostResponse;
import com.example.demo.post.service.PostServiceImpl;
import com.example.demo.post.repository.model.Post;
import com.example.demo.user.controller.UserController;
import com.example.demo.user.controller.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시물(posts)")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceImpl postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable long id) {
        return ResponseEntity
                .ok()
                .body(PostResponse.from(postService.getPostById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable long id, @RequestBody PostUpdateDto postUpdateDto) {
        return ResponseEntity
<<<<<<< HEAD:src/main/java/com/example/demo/post/controller/PostController.java
                .ok()
                .body(PostResponse.from(postService.updatePost(id, postUpdateDto)));
=======
            .ok()
            .body(toResponse(postService.update(id, postUpdateDto)));
>>>>>>> github/main:src/main/java/com/example/demo/controller/PostController.java
    }


}