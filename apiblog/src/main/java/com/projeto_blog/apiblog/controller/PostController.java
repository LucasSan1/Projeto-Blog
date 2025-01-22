package com.projeto_blog.apiblog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projeto_blog.apiblog.entity.PostEntity;
import com.projeto_blog.apiblog.service.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody @Valid PostEntity post){
        return postService.createPost(post);
    }
}
