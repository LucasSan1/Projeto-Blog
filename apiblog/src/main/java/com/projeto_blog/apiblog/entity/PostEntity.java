package com.projeto_blog.apiblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "author_name") // Define a junção com a tabela Author pelo nome de author_name
    @JsonIgnore  // Ignora o objeto author na serialização do json
    private User author;

    @JsonProperty("authorName")  // Adiciona um campo no json e define o dado a ser serializado
    public String getAuthorName() {
        return (author != null) ? author.getName() : null;
    }

    @JsonProperty("authorEmail")
    public String getAuthorEmail() {
        return (author != null) ? author.getEmail() : null;
    }

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String category;

    public PostEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
