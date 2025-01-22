package com.projeto_blog.apiblog.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projeto_blog.apiblog.entity.PostEntity;
import com.projeto_blog.apiblog.entity.User;
import com.projeto_blog.apiblog.repository.PostsRepository;
import com.projeto_blog.apiblog.repository.UserRepository;
import com.projeto_blog.apiblog.security.JwtTokenUtil; // Importando o JwtTokenUtil

@Service
public class PostService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;  // Injetando o JwtTokenUtil

    public PostService(PostsRepository postsRepository, UserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
        this.postsRepository = postsRepository;
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public ResponseEntity<String> createPost(PostEntity post, String token) {
        try {
            // Extrair o nome de usuário do token JWT
            String username = jwtTokenUtil.extractUsername(token);

            // Validar o token
            if (jwtTokenUtil.isTokenExpired(token)) {
                return new ResponseEntity<>("Token expirado", HttpStatus.UNAUTHORIZED);
            }

            // Buscar o usuário no banco de dados
            User user = userRepository.findByEmail(username);  // ou findById() caso use o ID

            if (user == null) {
                return new ResponseEntity<>("Usuário não encontrado!", HttpStatus.UNAUTHORIZED);
            }


            // Associar o autor (usuário) ao post
            post.setAuthor(user);

            // Salvar o post no banco de dados
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
}
