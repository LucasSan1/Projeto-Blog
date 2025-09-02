package com.projeto_blog.apiblog.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.projeto_blog.apiblog.service.ImageService;
import com.projeto_blog.apiblog.entity.PostEntity;
import com.projeto_blog.apiblog.repository.PostsRepository;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;
    private final PostsRepository postRepository;

    public ImageController(ImageService imageService, PostsRepository postRepository) {
        this.imageService = imageService;
        this.postRepository = postRepository;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam(value = "postId", required = false) Long postId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        try {
            String token = authHeader.replace("Bearer ", "");

            // busca o post se o ID foi fornecido
            PostEntity post = null;
            if (postId != null) {
                post = postRepository.findById(postId)
                        .orElseThrow(() -> new RuntimeException("Post não encontrado"));
            }

            return imageService.create(file, name, token, post);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Erro ao processar a imagem: " + e.getMessage());
        }
    }
}
