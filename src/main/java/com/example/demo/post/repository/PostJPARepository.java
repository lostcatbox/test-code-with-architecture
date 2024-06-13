package com.example.demo.post.repository;

import com.example.demo.post.repository.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJPARepository extends JpaRepository<PostEntity, Long> {

}