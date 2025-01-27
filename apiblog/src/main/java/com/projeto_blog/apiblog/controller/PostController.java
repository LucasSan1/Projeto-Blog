package com.projeto_blog.apiblog.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto_blog.apiblog.DTO.UpdatePostDTO;
import com.projeto_blog.apiblog.entity.PostEntity;
import com.projeto_blog.apiblog.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostEntity post, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
       
        String token = authHeader.replace("Bearer ", "");

        return postService.createPost(post, token);
    }

    @GetMapping
    public List<PostEntity> getAllPosts() {
        return postService.getAllPosts();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePosts(@PathVariable("id") Long id, @RequestBody UpdatePostDTO updatePostDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        
        String token = authHeader.replace("Bearer ", "");

        return postService.updatePosts(id, updatePostDTO, token);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        
        String token = authHeader.replace("Bearer ", "");

        return postService.deletePost(id, token);
    }
}
