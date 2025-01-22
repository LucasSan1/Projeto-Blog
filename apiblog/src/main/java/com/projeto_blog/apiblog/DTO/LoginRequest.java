package com.projeto_blog.apiblog.DTO;

public class LoginRequest { // Este DTO(Data Transfer Object) desestrutura o json que vem pelo RequestBody
    private String email;
    private String password;

    // Getter e Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
