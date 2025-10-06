package com.projeto_blog.apiblog.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.projeto_blog.apiblog.DTO.PostResponseDTO;
import com.projeto_blog.apiblog.DTO.UpdatePostDTO;
import com.projeto_blog.apiblog.entity.PostEntity;
import com.projeto_blog.apiblog.entity.User;
import com.projeto_blog.apiblog.exceptions.PostsException.PostNotFoundException;
import com.projeto_blog.apiblog.exceptions.PostsException.UnauthorizedAccessException;
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

    public ResponseEntity<PostResponseDTO> createPost(PostEntity post, String token) {
        try {

            if (jwtTokenUtil.isTokenExpired(token)) {
                return new ResponseEntity<>(new PostResponseDTO(null, "Token expirado!"), HttpStatus.UNAUTHORIZED);
            }

            // Extrair o nome de usuário do token JWT
            String username = jwtTokenUtil.extractUsername(token);
            User user = userRepository.findByEmail(username);

            if (user == null) {
                return new ResponseEntity<>(new PostResponseDTO(null, "Usuário não encontrado!"), HttpStatus.UNAUTHORIZED);
            }

            ZoneId Fzone = ZoneId.of("America/Sao_Paulo");
            String dateTime = ZonedDateTime.now(Fzone).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Associar o autor (usuário) ao post
            post.setAuthor(user);
            post.setDateTime(dateTime);

            PostEntity savedPost = postsRepository.save(post);

            // Retorna PostResponseDTO com o ID do post criado
            PostResponseDTO responseDTO = new PostResponseDTO(savedPost.getId(), "Post criado com sucesso!");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new PostResponseDTO(null, "Erro interno do Servidor: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> updatePosts(Long id, UpdatePostDTO updatePosts, String token) {

        String title = updatePosts.getTitle();
        String content = updatePosts.getContent();
        String category = updatePosts.getCategory();

        try{

            if (jwtTokenUtil.isTokenExpired(token)) {
                return new ResponseEntity<>("Token expirado!", HttpStatus.UNAUTHORIZED);
            }

            PostEntity post = postsRepository.findById(id).orElse(null);

            if (post == null) {
                throw new PostNotFoundException("Post não encontrado!");
            }
        
            // Obtem o email do usuario logado
            String currentUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // Verificar se o usuário autenticado é o autor do post
            if (!post.getAuthorEmail().equals(currentUser)) {
                throw new UnauthorizedAccessException("Você não tem permissão para editar este post!");
            }

            post.setTitle(title);
            post.setContent(content);
            post.setCategory(category);

            postsRepository.save(post);

            return new ResponseEntity<>("Post atualizado!", HttpStatus.OK);
            
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao atualizar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deletePost(Long id, String token) {
        try{

            if (jwtTokenUtil.isTokenExpired(token)) {
                return new ResponseEntity<>("Token expirado!", HttpStatus.UNAUTHORIZED);
            }

            PostEntity post = postsRepository.findById(id).orElse(null);

            if(post == null){
                throw new PostNotFoundException("Post não encontrado!");
            }

            String currentUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(!post.getAuthorEmail().equals(currentUser)){
                throw new UnauthorizedAccessException("Você não tem permissão para deletar este post!");
            }

            postsRepository.deleteById(id);

            return new ResponseEntity<>("Post Deletado!", HttpStatus.OK);

        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao deletar: " + e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    public List<PostEntity> getPostsByCategory(String category) {
        try {
            List<PostEntity> posts = postsRepository.findByCategory(category);
            
            if (posts == null || posts.isEmpty()) {
                throw new PostNotFoundException("Nenhum post encontrado para a categoria: " + category);
            }
    
            return posts;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar posts por categoria: " + e.getMessage());
        }
    }
}