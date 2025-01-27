package com.projeto_blog.apiblog.exceptions;

public class CommentsException {

    public static class CommentNotFoundException extends RuntimeException {
        public CommentNotFoundException(String message) {
            super(message);
        }
    }

}
