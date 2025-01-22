package com.projeto_blog.apiblog.DTO;

public class PostDTO {

    private Long id;
    private String title;
    private String content;
    private String category;
    private String authorName;  

    
    public PostDTO(Long id, String title, String content, String category, String authorName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.authorName = authorName;
    }
}