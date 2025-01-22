package com.projeto_blog.apiblog.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projeto_blog.apiblog.entity.PostEntity;
import com.projeto_blog.apiblog.repository.PostsRepository;



@Service
public class PostService {

    private PostsRepository postsRepository;
    private String author;

    public PostService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    public ResponseEntity<String> createPost(PostEntity post) {
        try {

            if(author == null){
                return new ResponseEntity<>("O usuario deve estar logado!", HttpStatus.UNAUTHORIZED);
            }

            postsRepository.save(post);
            return new ResponseEntity<>("Post criado com sucesso!", HttpStatus.CREATED);

        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao criar post: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
