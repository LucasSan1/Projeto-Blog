package com.projeto_blog.apiblog.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projeto_blog.apiblog.DTO.ErrorResponse;
import com.projeto_blog.apiblog.DTO.LoginRequest;
import com.projeto_blog.apiblog.DTO.LoginResponse;
import com.projeto_blog.apiblog.entity.User;
import com.projeto_blog.apiblog.repository.UserRepository;
import com.projeto_blog.apiblog.security.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private  UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Construtor que injeta o repositório UserRepository no serviço
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para criar um novo usuário
    public ResponseEntity<String> create(User user) {
        try {
            // Verifica se já existe um usuário com o mesmo email
            if (userRepository.existsByEmail(user.getEmail())) {
                return new ResponseEntity<>("Já existe uma conta com esse email.", HttpStatus.BAD_REQUEST); 
            } else if(userRepository.existsByName(user.getName())){
                return new ResponseEntity<>("O nome de usuário já está em uso.", HttpStatus.BAD_REQUEST);
            }

            // Criptografa a senha antes de salvar no banco
            String senhaCriptografada = passwordEncoder.encode(user.getPassword());
            user.setPassword(senhaCriptografada);

            userRepository.save(user); 
            return new ResponseEntity<>("Usuário criado com sucesso!", HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            // Captura exceções de violação de integridade (caso o email já esteja em uso, por exemplo)
            return new ResponseEntity<>("Erro: o email fornecido já está em uso.", HttpStatus.BAD_REQUEST); 
        }
    }

    // Método para recuperar todos os usuários
    public List<User> getAllUsers() {
        return userRepository.findAll(); 
    }

    // Método para realizar o login de um usuário
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {

        // Pega o usuario e a senha do DTO (LoginRequest)
        String usuarioRecebido = loginRequest.getUser();
        String senhaRecebida = loginRequest.getPassword();
      
        User user;

        // Busca o usuário no banco de dados com o email fornecido
        if(usuarioRecebido.contains("@")){
            user = userRepository.findByEmail(usuarioRecebido);
        } else {
            user = userRepository.findByName(usuarioRecebido);
        }

        // Caso o usuário não seja encontrado ou senha incorreta
        if (user == null || !passwordEncoder.matches(senhaRecebida, user.getPassword())) {
            ErrorResponse errorResponse = new ErrorResponse("Email ou senha errados");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        // Gera o token
        String token = JwtTokenUtil.generateToken(user.getEmail());
        userRepository.save(user);

        // Caso o login seja bem-sucedido, retorna a mensagem de logado com sucesso e o token
        LoginResponse loginResponse = new LoginResponse("Logado com sucesso!", token);
        return ResponseEntity.ok(loginResponse);
    }

    // Método para atualizar os dados de um usuário
    public ResponseEntity<String> updateUser(LoginRequest loginRequest) {

        String user = loginRequest.getUser();
        String senha = loginRequest.getPassword();

        // Tenta encontrar o usuário pelo ID
        User userExist = userRepository.findByEmail(user);

        // Se o usuário não for encontrado retorna o erro 
        if (userExist == null) {
            return new ResponseEntity<>("Usuário não encontrado.", HttpStatus.NOT_FOUND);
        }

        // Criptografa a senha antes de salvar no banco
        String senhaCriptografada = passwordEncoder.encode(senha);
        userExist.setPassword(senhaCriptografada); 
        userRepository.save(userExist); 
        
        return new ResponseEntity<>("Usuário atualizado com sucesso!", HttpStatus.OK);
    }

    // Método para deletar um usuário
    public ResponseEntity<?> deleteUser(LoginRequest loginRequest, String token) {

        String user = loginRequest.getUser();
        String senha = loginRequest.getPassword();

        User userExist = userRepository.findByEmail(user);
        
        // Se o usuário não for encontrado retorna o erro 
        if (userExist == null) {
            ErrorResponse errorResponse = new ErrorResponse("Usuário não encontrado.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        // Verifica se a senha está correta para confirmar o usuario a ser deletado
        if (!passwordEncoder.matches(senha, userExist.getPassword())) {
            ErrorResponse errorResponse = new ErrorResponse("Senha incorreta.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        // Deleta o usuário do banco de dados
        userRepository.delete(userExist); 
        return new ResponseEntity<>("Usuário deletado com sucesso!", HttpStatus.OK); 
    }

}
