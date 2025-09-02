package com.projeto_blog.apiblog.DTO;

public class PostResponseDTO {

    private Long postId;
    private String message;

    public PostResponseDTO(Long postId, String message) {
        this.postId = postId;
        this.message = message;
    }

    
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
}
