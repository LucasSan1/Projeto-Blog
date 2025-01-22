package com.projeto_blog.apiblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.projeto_blog.apiblog.entity.User;



public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email); // Função pra fazer busca por email

    User findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email); // Função pra verificar se email já existe

    User findByToken(String token); // Função pra buscar token
    
}
