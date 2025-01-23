# Projeto Blog - API de Blog com Spring Boot

## Descrição

Este é um projeto de blog simples, desenvolvido com **Java 17**, **Spring Boot** e **Maven**. A API permite que os usuários se registrem, criem posts e comentem nesses posts. (Ainda está em desenvolvimento!! funcionalidades como comentarios ainda está sendo desenvolvida)

## Funcionalidades

- **Cadastro de Usuário**: Os usuários podem se cadastrar com um nome, e-mail e senha.
- **Autenticação**: Utiliza JWT (JSON Web Token) para autenticação de usuários.
- **Criação de Posts**: Os usuários autenticados podem criar posts, incluindo título, conteúdo e categoria.
- **Visualização de Posts**: Permite visualizar todos os posts existentes.
- **Comentários nos Posts**: Os usuários podem comentar nos posts existentes.
- **Edição e Deleção de Posts**: Os usuários podem editar ou deletar seus próprios posts.
- **Visibilidade e Proteção de Rotas**: Algumas rotas são públicas (como visualização de posts), enquanto outras exigem autenticação (como criação de posts, edição e deleção).
  
## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot** (para a construção da API)
- **Maven** (gerenciamento de dependências e build)
- **MySQL** (para persistência dos dados)
- **JWT (JSON Web Tokens)** (para autenticação)
- **Spring Security** (para proteger as rotas)
- **Swagger/OpenAPI** (para documentação da API)

### Passos para rodar o projeto

1. Clone este repositório:

```bash
git clone https://github.com/LucasSan1/Projeto-Blog.git
cd projeto-blog
```

2. Crie o Banco de Dados Mysql

```bash
CREATE DATABASE blog;
```
3. Configure o application.properties com suas credenciais do banco de dados
```bash
spring.datasource.url=jdbc:mysql://localhost3306/blog
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

4. Compile e execulte o projeto

    Com Maven, você pode compilar e rodar o projeto com os seguintes comandos:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
    ou navegue até o arquivo ApiblogApplication.java e execute-o.

### Documentação

Após execultar o projeto, você pode acessar a documentação da API em [http://localhost:8080/swagger-ui.html]