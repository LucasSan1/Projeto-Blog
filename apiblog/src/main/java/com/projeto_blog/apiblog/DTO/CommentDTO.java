package com.projeto_blog.apiblog.DTO;

import com.projeto_blog.apiblog.entity.CommentEntity;

public class CommentDTO {

    private Long id;
    private String content;
    private String dateTime;
    private String authorName;

    public CommentDTO() {
    }

    public CommentDTO(CommentEntity comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.dateTime = comment.getDatetime();
        this.authorName = comment.getAuthorName();
    }
    
    public String getDateTime() {
        return dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String contentt) {
        content = contentt;
    }
    
}
