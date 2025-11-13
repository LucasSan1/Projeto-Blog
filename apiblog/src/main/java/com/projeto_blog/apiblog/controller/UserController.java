package com.projeto_blog.apiblog.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projeto_blog.apiblog.DTO.LoginRequest;
import com.projeto_blog.apiblog.entity.User;
import com.projeto_blog.apiblog.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user") // Define o endpoint base para as requisições ("/user")
public class UserController {

    private UserService userService; // Declara a dependência do serviço de usuários

    public UserController(UserService userService) { 
        this.userService = userService; // Inicia o serviço de usuários via injeção de dependência
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid User user) { // Recebe um objeto User no corpo da requisição e valida os dados com @Valid
        return userService.create(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    @PutMapping("/forgetPass")
    public ResponseEntity<String> updateUser(@RequestBody LoginRequest loginRequest) { // Recebe o id do usuário e o objeto User no corpo da requisição e valida os dados com @Valid
        return userService.updateUser(loginRequest);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser(@RequestBody LoginRequest loginRequest, @RequestHeader("Authorization") String token) {
        return userService.deleteUser(loginRequest, token);

    }
}
