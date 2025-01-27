package com.projeto_blog.apiblog.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.projeto_blog.apiblog.DTO.CommentDTO;
import com.projeto_blog.apiblog.entity.CommentEntity;
import com.projeto_blog.apiblog.entity.PostEntity;
import com.projeto_blog.apiblog.entity.User;
import com.projeto_blog.apiblog.exceptions.CommentsException.CommentNotFoundException;
import com.projeto_blog.apiblog.exceptions.PostsException.UnauthorizedAccessException;
import com.projeto_blog.apiblog.repository.CommentsRepository;
import com.projeto_blog.apiblog.repository.PostsRepository;
import com.projeto_blog.apiblog.repository.UserRepository;
import com.projeto_blog.apiblog.security.JwtTokenUtil;

@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public CommentsService(CommentsRepository commentsRepository, PostsRepository postsRepository, UserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
        this.commentsRepository = commentsRepository;
        this.postsRepository = postsRepository;
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public ResponseEntity<String> createComment(CommentDTO commentDTO, String token, Long postId) {
        try {

            String username = jwtTokenUtil.extractUsername(token);

            if (jwtTokenUtil.isTokenExpired(token)) {
                return new ResponseEntity<>("Token expirado!", HttpStatus.UNAUTHORIZED);
            }

            User user = userRepository.findByEmail(username);
            PostEntity post = postsRepository.findById(postId).orElse(null);
            
            if (post == null) {
                return new ResponseEntity<>("Post não encontrado!", HttpStatus.NOT_FOUND);
            }

            CommentEntity comment = new CommentEntity();
            comment.setContent(commentDTO.getContent());
            
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            comment.setDatetime(dateTime);

            comment.setAuthor(user);
            comment.setPost(post);

            commentsRepository.save(comment);
            return new ResponseEntity<>("Comentário salvo!", HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro interno do servidor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> updateComment(CommentDTO commentDTO, Long commentId, String token) {

        String content = commentDTO.getContent();

        try{

            if (jwtTokenUtil.isTokenExpired(token)) {
                return new ResponseEntity<>("Token expirado!", HttpStatus.UNAUTHORIZED);
            }

            CommentEntity comment = commentsRepository.findById(commentId).orElse(null);

            if(comment == null){
                return new ResponseEntity<>("Comentário não encontrado!", HttpStatus.NOT_FOUND);
            }

            String currentUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!comment.getAuthorEmail().equals(currentUser)) {
                throw new UnauthorizedAccessException("Você não tem permissão para editar este comentario.");
            }
            
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            comment.setContent(content);
            comment.setDatetime(dateTime);
            
            commentsRepository.save(comment);

            return new ResponseEntity<>("Comentário atualizado!", HttpStatus.OK);

        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Erro atualizar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteComment(Long commentId, String token) {
        try{

            if (jwtTokenUtil.isTokenExpired(token)) {
                return new ResponseEntity<>("Token expirado!", HttpStatus.UNAUTHORIZED);
            }

            CommentEntity comment = commentsRepository.findById(commentId).orElse(null);

            if(comment == null){
                throw new CommentNotFoundException("Comentario não encontrado!");
            }

            String currentUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(!comment.getAuthorEmail().equals(currentUser)){
                throw new UnauthorizedAccessException("Você não tem permissão para deletar este comentario!");
            }

            commentsRepository.deleteById(commentId);
            return new ResponseEntity<>("Comentario Deletado!", HttpStatus.OK);

        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Erro deletar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<CommentEntity> getAllComments(){
        return commentsRepository.findAll();
    }

}
