package com.projeto_blog.apiblog.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.projeto_blog.apiblog.entity.ImagesEntity;
import com.projeto_blog.apiblog.entity.PostEntity;
import com.projeto_blog.apiblog.entity.User;
import com.projeto_blog.apiblog.repository.ImageRepository;
import com.projeto_blog.apiblog.repository.UserRepository;
import com.projeto_blog.apiblog.security.JwtTokenUtil;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final JwtTokenUtil jwtTokenUtil; 
    private final UserRepository userRepository;

    public ImageService(ImageRepository imageRepository, JwtTokenUtil jwtTokenUtil, UserRepository userRepository){
        this.imageRepository = imageRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

   public ResponseEntity<String> create(MultipartFile[] files, String token, PostEntity post) {
        try {
            if (jwtTokenUtil.isTokenExpired(token)) {
                return new ResponseEntity<>("Token expirado", HttpStatus.UNAUTHORIZED);
            }

            String userEmail = jwtTokenUtil.extractUsername(token);
            User user = userRepository.findByEmail(userEmail);

            if (user == null) {
                return new ResponseEntity<>("Usuário não encontrado", HttpStatus.UNAUTHORIZED);
            }

            for (MultipartFile file : files) {
                ImagesEntity image = new ImagesEntity();
                image.setName(file.getOriginalFilename());
                image.setImage(file.getBytes());
                image.setUser(user);
                image.setPost(post);
                
                imageRepository.save(image);
            }

            return new ResponseEntity<>("Imagens salvas com sucesso!", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro interno do Servidor: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


   public ResponseEntity<String> deleteImages(List<Long> imageIds, String token) {
        try {
            if (jwtTokenUtil.isTokenExpired(token)) {
                return new ResponseEntity<>("Token expirado", HttpStatus.UNAUTHORIZED);
            }

            String userEmail = jwtTokenUtil.extractUsername(token);
            User user = userRepository.findByEmail(userEmail);

            if (user == null) {
                return new ResponseEntity<>("Usuário não encontrado", HttpStatus.UNAUTHORIZED);
            }

            // Percorre e deleta as imagens
            for (Long id : imageIds) {
                ImagesEntity image = imageRepository.findById(id).orElse(null);
                if (image == null) continue;

                // Verifica se a imagem pertence ao usuário antes de deletar
                if (!image.getPost().getAuthorEmail().equals(userEmail)) {
                    return new ResponseEntity<>(
                        "Você não tem permissão para deletar a imagem ID " + id,
                        HttpStatus.FORBIDDEN
                    );
                }

                imageRepository.delete(image);
            }

            return new ResponseEntity<>("Imagens deletadas com sucesso!", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro interno do servidor: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public List<ImagesEntity> getAllImages(){
        return imageRepository.findAll();
    }
}
