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

import com.projeto_blog.apiblog.DTO.CommentDTO;
import com.projeto_blog.apiblog.entity.CommentEntity;
import com.projeto_blog.apiblog.service.CommentsService;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping("/{postId}")
    public ResponseEntity<String> createComment(@PathVariable("postId") Long postId, @RequestBody CommentDTO commentDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.replace("Bearer ", "");

        return commentsService.createComment(commentDTO, token, postId);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentDTO commentDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        
        String token = authHeader.replace("Bearer ", "");

        return commentsService.updateComment(commentDTO, commentId, token);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        
        String token = authHeader.replace("Bearer ", "");

        return commentsService.deleteComment(commentId, token);
    }

    @GetMapping
    public List<CommentEntity> getAllComments() {
        return commentsService.getAllComments();
    }
}
  
