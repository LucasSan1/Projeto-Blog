package com.projeto_blog.apiblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto_blog.apiblog.entity.CommentEntity;

public interface CommentsRepository extends JpaRepository<CommentEntity, Long> {

    
}
