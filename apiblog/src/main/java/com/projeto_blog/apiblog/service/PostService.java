package com.projeto_blog.apiblog.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.projeto_blog.apiblog.DTO.UpdatePostDTO;
import com.projeto_blog.apiblog.entity.PostEntity;
import com.projeto_blog.apiblog.entity.User;
import com.projeto_blog.apiblog.exceptions.PostsException.*;
import com.projeto_blog.apiblog.repository.PostsRepository;
import com.projeto_blog.apiblog.repository.UserRepository;
import com.projeto_blog.apiblog.security.JwtTokenUtil; 

@Service
public class PostService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil; 

    public PostService(PostsRepository postsRepository, UserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
        this.postsRepository = postsRepository;
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public ResponseEntity<String> createPost(PostEntity post, String token) {
        try {
            // Extrair o nome de usuário do token JWT
            String username = jwtTokenUtil.extractUsername(token);


            if (jwtTokenUtil.isTokenExpired(token)) {
                return new ResponseEntity<>("Token expirado", HttpStatus.UNAUTHORIZED);
            }

            User user = userRepository.findByEmail(username); 

            if (user == null) {
                return new ResponseEntity<>("Usuário não encontrado!", HttpStatus.UNAUTHORIZED);
            }

            // Associar o autor (usuário) ao post
            post.setAuthor(user);

            postsRepository.save(post);
            return new ResponseEntity<>("Post criado com sucesso!", HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao criar post: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<PostEntity> getAllPosts() {
        return postsRepository.findAll();
    }

    public ResponseEntity<String> updatePosts(Long id, UpdatePostDTO updatePosts) {

        String title = updatePosts.getTitle();
        String content = updatePosts.getContent();
        String category = updatePosts.getCategory();

        PostEntity post = postsRepository.findById(id).orElse(null);

        if (post == null) {
            throw new PostNotFoundException("Post não encontrado.");
        }
    
        String currentUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Verificar se o usuário autenticado é o autor do post
        if (!post.getAuthorEmail().equals(currentUser)) {
            throw new UnauthorizedAccessException("Você não tem permissão para editar este post.");
        }

        post.setTitle(title);
        post.setContent(content);
        post.setCategory(category);

        postsRepository.save(post);

        return new ResponseEntity<>("Post atualizado!", HttpStatus.OK);
    }
}
