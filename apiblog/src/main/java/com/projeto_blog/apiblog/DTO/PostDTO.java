package com.projeto_blog.apiblog.DTO;

import java.util.List;
import java.util.stream.Collectors;

import com.projeto_blog.apiblog.entity.ImagesEntity;
import com.projeto_blog.apiblog.entity.PostEntity;

public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private String authorEmail;
    private String dateTime;
    private List<Long> imagesIds;
    private List<CommentDTO> comments;
    private String category;

    public PostDTO(PostEntity post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorName = post.getAuthorName();
        this.dateTime = post.getDateTime();
        this.authorEmail = post.getAuthorEmail();
        this.category = post.getCategory();
        
        this.imagesIds = post.getImages().stream()
                             .map(ImagesEntity::getId)
                             .collect(Collectors.toList());
        
        this.comments = post.getComments().stream()
                            .map(CommentDTO::new) // converte cada CommentEntity para CommentDTO
                            .collect(Collectors.toList());
    }
    
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Long> getImagesIds() {
        return imagesIds;
    }

    public void setImagesIds(List<Long> imagesIds) {
        this.imagesIds = imagesIds;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    
}

