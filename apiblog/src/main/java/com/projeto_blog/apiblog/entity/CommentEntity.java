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
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;
    private String datetime;
    
    @ManyToOne
    @JoinColumn(name = "author_name")
    @JsonIgnore
    private User author;

    @JsonProperty("author_name")
    public String getAuthorName() {
        return author.getName();
    }

    @JsonProperty("author_email")
    public String getAuthorEmail() {
        return author.getEmail();
    }

    @ManyToOne
    @JoinColumn(name = "post") 
    @JsonIgnore
    private PostEntity post;

    @JsonProperty("post_title")
    public String getpPostTitle(){
        return post.getTitle();
    }

    @JsonProperty("post_id")
    public Long getPostId(){
        return post.getId();
    }

    public CommentEntity() {
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

}
