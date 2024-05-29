package com.example.demo.post.controller;

import com.example.demo.post.controller.dto.request.PostUpdateDto;
import com.example.demo.post.controller.dto.response.PostResponse;
import com.example.demo.post.service.PostServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                .ok()
                .body(PostResponse.from(postService.updatePost(id, postUpdateDto)));
    }


}