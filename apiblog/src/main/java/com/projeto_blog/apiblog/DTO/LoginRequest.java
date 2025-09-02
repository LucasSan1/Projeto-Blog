package com.projeto_blog.apiblog.DTO;

public class LoginRequest { // Este DTO(Data Transfer Object) desestrutura o json que vem pelo RequestBody
    private String user;
    private String password;

    // Getter e Setter
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
