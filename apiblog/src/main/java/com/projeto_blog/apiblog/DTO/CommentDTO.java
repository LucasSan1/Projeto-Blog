package com.projeto_blog.apiblog.DTO;

import com.projeto_blog.apiblog.entity.CommentEntity;

public class CommentDTO {

    private String Content;
    private String dateTime;
    private String authorName;

    public CommentDTO(CommentEntity comment) {
        this.Content = comment.getContent();
        this.dateTime = comment.getDatetime();
        this.authorName = comment.getAuthorName();
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

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
    
}
